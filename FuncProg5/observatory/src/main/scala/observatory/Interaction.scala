package observatory

import com.sksamuel.scrimage.{Image, Pixel}

/**
  * 3rd milestone: interactive visualization
  */
object Interaction {

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */
  def tileLocation(tile: Tile): Location = {
    Location(
      math.toDegrees(math.atan(math.sinh(math.Pi * (1.0 - 2.0 * tile.y.toDouble / (1 << tile.zoom))))),
      tile.x.toDouble / (1 << tile.zoom) * 360.0 - 180.0
    )
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param tile Tile coordinates
    * @return A 256×256 image showing the contents of the given tile
    */
  def tile(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)], tile: Tile): Image = {
    /**
      * Calculate all the coordinates for the given tile from X and Y (256x256)
      * for each coordinate, map the tile to a location (tileLocation)
      * then, map the locations to temperatures (predictTemperature)
      * them map the temperatures to colors (interpolateColor)
      * then map the colors to pixels with alpha 127
      * Then map to an Array
      */

    val width = 256
    val height = 256
    val size = width * height

    def getTileByIndex(index: Int): Tile = {
      val x = index % width
      val y = math.floor(index / width).toInt
      Tile(tile.x + x, tile.y + y, tile.zoom + 8)
    }

    val pixelsArray = (0 until size)
      .par
      .map(getTileByIndex)
      .map(tileLocation)
      .map(location => Visualization.predictTemperature(temperatures, location))
      .map(temperature => Visualization.interpolateColor(colors, temperature).toPixel(127))
      // .map(_ => Pixel(0, 0, 0, 0))
      .toArray

    Image(width, height, pixelsArray)
  }

  /**
    * Generates all the tiles for zoom levels 0 to 3 (included), for all the given years.
    * @param yearlyData Sequence of (year, data), where `data` is some data associated with
    *                   `year`. The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year, a zoom level, the x and
    *                      y coordinates of the tile and the data to build the image from
    */
  def generateTiles[Data](
    yearlyData: Iterable[(Year, Data)],
    generateImage: (Year, Tile, Data) => Unit
  ): Unit = {
    ???
  }

}
