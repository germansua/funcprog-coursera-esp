package observatory

import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

case class Station(stnid: Option[String], wbanid: Option[String], latitude: Option[Double], longitude: Option[Double])

object Station {
  val schema: StructType = StructType(List(
    StructField("stnid", StringType, nullable = true),
    StructField("wbanid", StringType, nullable = true),
    StructField("latitude", DoubleType, nullable = true),
    StructField("longitude", DoubleType, nullable = true)
  ))
}