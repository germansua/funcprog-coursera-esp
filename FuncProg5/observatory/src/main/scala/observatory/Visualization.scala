package observatory

import com.sksamuel.scrimage.Image

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

    def calculateAccumulators(values: Iterable[(Double, Temperature)],
                              upperAcc: Double, lowerAcc: Double): (Double, Double) = values match {
      case x :: xs => calculateAccumulators(xs, upperAcc + (x._2 / pow(x._1, 2)), lowerAcc + 1 / pow(x._1, 2))
      case Nil => (upperAcc, lowerAcc)
    }

    val result = calculateAccumulators(values, 0, 0)
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
    ???
  }

  /**
    * @param temperatures Known temperatures
    * @param colors       Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): Image = {
    ???
  }
}

