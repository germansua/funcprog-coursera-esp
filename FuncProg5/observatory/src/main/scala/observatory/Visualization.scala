package observatory

import com.sksamuel.scrimage.{Image, Pixel}

import scala.math._

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  private val P: Int = 2
  private val meanEarthRadiusKMs: Double = 6371

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location     Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    // If the location is into the temperatures, returns that value
    // Else, apply inverse distance weighting and approximate the temperature value
    temperatures
      .find(_._1 == location)
      .map(_._2)
      .getOrElse(calculatePrediction(temperatures, location))
  }

  private def calculatePrediction(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    // Calculate the distance for each location
    // If there's a location with less than 1km, return that value as the prediction
    // Otherwise, calculate the described formula for all points
    calculateInverseDistanceWeighting {
      temperatures
        .map(temp => (calculateDistanceBetweenLocations(temp._1, location), temp._2))
    }

    //      .find(iter => iter._1 < 1000)
    //      .map(_._2)
    //      .getOrElse(calculateInverseDistanceWeighting())
  }

  private def calculateInverseDistanceWeighting(values: Iterable[(Double, Temperature)]): Temperature = {
    // zp: Value of predicted point
    // zi: Value of i measured point
    // d(i): Distance of i measured to the predicted point
    // P: Power function
    // zp = sum( zi / pow(di, P) ) / sum( 1 / pow(di, P) )

    //    def calculateAccumulators(values: Iterable[(Double, Temperature)],
    //                              upperAcc: Double, lowerAcc: Double): (Double, Double) = values match {
    //      case x :: xs => calculateAccumulators(xs, upperAcc + (x._2 / pow(x._1, 2)), lowerAcc + 1 / pow(x._1, 2))
    //      case Nil => (upperAcc, lowerAcc)
    //    }
    //    val result = calculateAccumulators(values, 0, 0)

    def calculateAccumulators(values: Iterable[(Double, Temperature)]): (Double, Double) = {
      values.par.foldLeft((0.0, 0.0)) {
        (acc: (Double, Double), x: (Double, Temperature)) => (acc._1 + (x._2 / pow(x._1, 2)), acc._2 + 1 / pow(x._1, 2))
      }
    }

    val result = calculateAccumulators(values)
    result._1 / result._2
  }

  def calculateDistanceBetweenLocations(location1: Location, location2: Location): Double = {
    calculateDistance(calculateCentralAngle(location1, location2))
  }

  private def calculateCentralAngle(location1: Location, location2: Location): Double = {
    val lat1 = toRadians(location1.lat)
    val lon1 = toRadians(location1.lon)
    val lat2 = toRadians(location2.lat)
    val lon2 = toRadians(location2.lon)
    acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(abs(lon1 - lon2)))
  }

  private def calculateDistance(centralAngle: Double): Double = meanEarthRadiusKMs * centralAngle

  /**
    * @param points Pairs containing a value and its associated color
    * @param value  The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color = {
    points.
      find(_._1 == value)
      .map(_._2)
      .getOrElse(calculateColorInterpolation(points, value))
  }

  def calculateColorInterpolation(points: Iterable[(Temperature, Color)], value: Temperature): Color = {
    //var limits = calculateLimits(points, value)
    //var lowerLimit = limits._1
    //var upperLimit = limits._2

    val (lowerLimit, upperLimit) = calculateLimits(points, value)
    calculateLinearInterpolation(lowerLimit, upperLimit, value)
  }

  def calculateLimits(points: Iterable[(Temperature, Color)], value: Temperature): (Option[(Temperature, Color)], Option[(Temperature, Color)]) = {
    val iters = points.par.partition(item => item._1 < value) // HERE!!!!

    val lowerLimit = if (!iters._1.isEmpty) Option(iters._1.reduce(reduceMaxTuple)) else Option.empty
    val upperLimit = if (!iters._2.isEmpty) Option(iters._2.reduce(reduceMinTuple)) else Option.empty

    (lowerLimit, upperLimit)
  }

  def calculateLinearInterpolation(lowerLimit: Option[(Temperature, Color)],
                                   upperLimit: Option[(Temperature, Color)],
                                   value: Temperature): Color = {

    if (lowerLimit.isEmpty) upperLimit.get._2
    else if (upperLimit.isEmpty) lowerLimit.get._2
    else {
      val lowerLimitTemperature = lowerLimit.get._1
      val lowerLimitColor = lowerLimit.get._2
      val upperLimitTemperature = upperLimit.get._1
      val upperLimitColor = upperLimit.get._2

      val red: Int = math.ceil((upperLimitColor.red - lowerLimitColor.red) * (value - lowerLimitTemperature) / (upperLimitTemperature - lowerLimitTemperature) + lowerLimitColor.red).toInt
      val green: Int = math.ceil((upperLimitColor.green - lowerLimitColor.green) * (value - lowerLimitTemperature) / (upperLimitTemperature - lowerLimitTemperature) + lowerLimitColor.green).toInt
      val blue: Int = math.ceil((upperLimitColor.blue - lowerLimitColor.blue) * (value - lowerLimitTemperature) / (upperLimitTemperature - lowerLimitTemperature) + lowerLimitColor.blue).toInt

      Color(red, green, blue).sanitize
    }
  }

  private def reduceMinTuple(tuple1: (Temperature, Color), tuple2: (Temperature, Color)): (Temperature, Color) = {
    reduceTempColorTuples((a, b) => a < b)(tuple1, tuple2)
  }

  private def reduceMaxTuple(tuple1: (Temperature, Color), tuple2: (Temperature, Color)): (Temperature, Color) = {
    reduceTempColorTuples((a, b) => a > b)(tuple1, tuple2)
  }

  private def reduceTempColorTuples(f: (Temperature, Temperature) => Boolean)
                                   (tuple1: (Temperature, Color), tuple2: (Temperature, Color)): (Temperature, Color) = {
    if (f(tuple1._1, tuple2._1)) tuple1 else tuple2
  }

  /**
    * @param temperatures Known temperatures
    * @param colors       Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): Image = {

    /**
      * Create an array of the size of the image
      * for each point(x, y) map it to a location
      * for each location calculate the temperature
      * for each temperature calculate the color interpolation
      * map each color to a pixel
      * create the image based on the Array
      */

    val width = 360
    val height = 180
    val size = width * height

    val pixelsArray =
      (0 until size)
        .map(i => fromXYLocationToGeoLocation(i, width, height))
        .map(location => predictTemperature(temperatures, location))
        .map(temperature => interpolateColor(colors, temperature))
        .map(_.toPixel())
        .toArray

    Image(width, height, pixelsArray)
  }

  def fromXYLocationToGeoLocation(index: Int, width: Int, height: Int): Location = {
    val x = index % width
    val y = math.floor(index / width).toInt
    Location(height / 2 - y, x - width / 2)
  }
}