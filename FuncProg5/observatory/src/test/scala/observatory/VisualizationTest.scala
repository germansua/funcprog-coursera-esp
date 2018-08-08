package observatory


import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

trait VisualizationTest extends FunSuite with Checkers {

  test("Calculate distance between Toronto and Johannesburg Test") {
    val toronto: Location = Location(43.677, -79.630)
    val johannesburg: Location = Location(-26.133, 28.242)
    assertResult(13368.752440074331)(Visualization.calculateDistanceBetweenLocations(toronto, johannesburg))
  }

  test("Calculate distance between Bogota and London Test") {
    val bogota: Location = Location(4.7110, 74.0721)
    val london: Location = Location(51.5074, 0.1278)
    assertResult(8490.70099307933)(Visualization.calculateDistanceBetweenLocations(bogota, london))
  }

  /*
      [#2 - Raw data display] exceeding the greatest value of a color scale should return the color associated with
      the greatest value [Observed Error] GeneratorDrivenPropertyCheckFailedException was thrown during property evaluation.
      (VisualizationTest.scala:32) Falsified after 0 successful property evaluations.
      Location: (VisualizationTest.scala:32)
      Occurred when passed generated values ( arg0 = -55.93412100934578, arg1 = -69.83012893073845 )
      Label of failing property: Incorrect predicted color: Color(0,0,209). Expected: Color(0,0,255) (
      scale = List((-69.83012893073845,Color(255,0,0)), (-55.93412100934578,Color(0,0,255))), value = -45.93412100934578) [Lost Points] 2


      [Test Description] [#2 - Raw data display] basic color interpolation [Observed Error] Color(127,0,127) did not equal Color(128,0,128) [Lost Points] 1


      [Test Description] [#2 - Raw data display] exceeding the greatest value of a color scale should return the color associated with
      the greatest value [Observed Error] GeneratorDrivenPropertyCheckFailedException was thrown during property evaluation.
      (VisualizationTest.scala:32) Falsified after 0 successful property evaluations.
      Location: (VisualizationTest.scala:32)
      Occurred when passed generated values ( arg0 = 1.0, arg1 = 24.544260795451137 )
      Label of failing property: Incorrect predicted color: Color(0,0,0). Expected: Color(255,0,0)
      (scale = List((1.0,Color(255,0,0)), (24.544260795451137,Color(0,0,255))), value = -9.0) [Lost Points] 2

      Corner Case when one on the lists are empty!!!!

  */

  test("calculateColorInterpolation") {

    var red = Color(255, 0, 0)
    var green = Color(0, 255, 0)
    var blue = Color(0, 0, 255)

    var points = Iterable((-1.0, red), (1.0, red), (2.0, green), (3.0, blue))

    // println ( Visualization.calculateColorInterpolation(points, -5.0) )


    var list = List((-69.83012893073845,Color(255,0,0)), (-55.93412100934578,Color(0,0,255)))
    var value = -45.93412100934578

    var c = Visualization.interpolateColor(list, value)
    println(c)
  }
}