package observatory

/**
  * 4th milestone: value-added information
  */
object Manipulation {

  /**
    * @param temperatures Known temperatures
    * @return A function that, given a latitude in [-89, 90] and a longitude in [-180, 179],
    *         returns the predicted temperature at this location
    */
  def makeGrid(temperatures: Iterable[(Location, Temperature)]): GridLocation => Temperature = {
    val grid = (
      for {
        lat <- -89 to 90
        lon <- -180 to 179
      } yield (GridLocation(lat, lon), Visualization.predictTemperature(temperatures, Location(lat, lon)))
    ).toMap

    //    def gridLocToTemperature(gridLocation: GridLocation): Temperature = grid(gridLocation)
    //    gridLocToTemperature
    gridLocation => grid(gridLocation)
  }

  /**
    * @param temperaturess Sequence of known temperatures over the years (each element of the collection
    *                      is a collection of pairs of location and temperature)
    * @return A function that, given a latitude and a longitude, returns the average temperature at this location
    */
  def average(temperaturess: Iterable[Iterable[(Location, Temperature)]]): GridLocation => Temperature = {
    /**
      * Parallelize computing of grids for each year
      * Calculate average value for each grid location value (reduce)
      * Cache the result
      * Return the function that gets the values from the cache
      */
    val size = temperaturess.size
    val gridsPerYear = temperaturess.map(makeGrid)
    gridLocation => gridsPerYear.map(_(gridLocation)).sum / size
  }

  /**
    * @param temperatures Known temperatures
    * @param normals A grid containing the “normal” temperatures
    * @return A grid containing the deviations compared to the normal temperatures
    */
  def deviation(temperatures: Iterable[(Location, Temperature)], normals: GridLocation => Temperature): GridLocation => Temperature = {
    /**
      * Transform temperatures into (GridLocation, Temperature) pair
      * Get the corresponding temperature with the GridLocation using normals
      * Calculate de deviation
      * return a new Function
      */
    def calculateDeviation(temperature: Temperature, normal: Temperature): Temperature = temperature - normal

//    val deviations = temperatures
//      .map(iter => (iter._1.toGridLocation, iter._2))
//      .map(pair => (pair._1, pair._2, normals(pair._1)))
//      .map(t3 => (t3._1, calculateDeviation(t3._2, t3._3)))
//      .toMap
//    gridLocation => deviations(gridLocation)

      val temperaturesToCompare = makeGrid(temperatures)
      gridLocation => calculateDeviation(temperaturesToCompare(gridLocation), normals(gridLocation))
  }
}

