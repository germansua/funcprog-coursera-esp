package observatory

import java.nio.file.Paths
import java.sql.Timestamp
import java.time.{LocalDate, ZoneId}

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, SparkSession}

/**
  * 1st milestone: data extraction
  */
object Extraction {

  val spark: SparkSession =
    SparkSession
      .builder()
      .appName("Extraction")
      .config("spark.master", "local")
      .getOrCreate()

  import spark.implicits._

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {
    // Read Stations
    //      STN identifier	WBAN identifier	Latitude	Longitude
    //      weather stations are uniquely identified by the compound key (STN, WBAN).
    //      ignore data coming from stations that have no GPS coordinates
    val stations: Dataset[Station] = readStations(stationsFile)

    // Read Temperature
    //      STN identifier	WBAN identifier	Month	Day	Temperature (in degrees Fahrenheit) -> Convert this to °C
    val temperatureData: Dataset[TemperatureData] = readTemperatures(temperaturesFile)

    // Join Stations and temperature on (STN, WBAN)
    // Generate an Iterable with the expected output
    stations
      .join(temperatureData, stations("stnid").eqNullSafe(temperatureData("stnid")) && stations("wbanid").eqNullSafe(temperatureData("wbanid")))
      .map(joinRow => (
        Timestamp.valueOf(LocalDate.of(year, joinRow.getAs[Int]("month"), joinRow.getAs[Int]("day")).atStartOfDay),
        // LocalDate.of(year, joinRow.getAs[Int]("month"), joinRow.getAs[Int]("day")), This because the "No Encoder found for java.time.LocalDate"
        Location(joinRow.getAs[Double]("latitude"), joinRow.getAs[Double]("longitude")),
        fromFahrenheitToCelsius(joinRow.getAs[Double]("temperature"))
      )
      )
      .collect
      .map(values => (values._1.toInstant.atZone(ZoneId.systemDefault()).toLocalDate, values._2, values._3))
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    // Average temperature at each location, over a year
    // Should be grouped by Location and Year
    // Year is part of LocalDate
    // Map the current data to year (from LocalDate), Location and Temperature
    // Then groupBy year and Location
    // And finally aggretate by temperature
    spark
      .sparkContext
      .parallelize(records.toSeq)
      .map(record => (record._1.getYear, record._2, record._3))
      .toDF("Year", "Location", "Temperature")
      .groupBy('Year, 'Location)
      .agg(
        'Year,
        'Location,
        avg('Temperature).as("Temperature")
      )
      .select('Location.as[Location], 'Temperature.as[Temperature])
      .collect()
  }

  private def readStations(stationsFile: String): Dataset[Station] = {
    val stationsFilePath = Paths.get(getClass.getResource(stationsFile).toURI).toString
    // spark.sparkContext.textFile()
    spark
      .read
      .option("header", value = false)
      .schema(Station.schema)
      .csv(stationsFilePath)
      .as[Station]
      .filter((station: Station) => station.latitude.isDefined && station.longitude.isDefined)
  }

  private def readTemperatures(temperaturesFile: String): Dataset[TemperatureData] = {
    val temperatureFilePath = Paths.get(getClass.getResource(temperaturesFile).toURI).toString
    spark
      .read
      .option("header", value = false)
      .schema(TemperatureData.schema)
      .csv(temperatureFilePath)
      .as[TemperatureData]
      .filter((tData: TemperatureData) => tData.temperature != 9999.9)
  }

  private def fromFahrenheitToCelsius(fahrenheitTemp: Temperature): Temperature = (fahrenheitTemp - 32) * 5 / 9
}
