package observatory

import org.apache.spark.sql.types._

case class TemperatureData(stnid: Option[String], wbanid: Option[String], month: Option[Int], day: Option[Int], temperature: Double)

object TemperatureData {
  val schema: StructType = StructType(List(
    StructField("stnid", StringType, nullable = true),
    StructField("wbanid", StringType, nullable = true),
    StructField("month", IntegerType, nullable = true),
    StructField("day", IntegerType, nullable = true),
    StructField("temperature", DoubleType, nullable = true)
  ))
}
