package observatory


import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

trait VisualizationTest extends FunSuite with Checkers {

  test("Calculate Central Angle between Toronto and Johannesburg Test") {
    val toronto: Location = Location(43.677, -79.630)
    val johannesburg: Location = Location(-26.133, 28.242)
    assertResult(13368.752440074331)(Visualization.calculateCentralAngle(toronto, johannesburg))
  }

  test("Calculate Central Angle between Bogota and London Test") {
    val bogota: Location = Location(4.7110, 74.0721)
    val london: Location = Location(51.5074, 0.1278)
    assertResult(8490.70099307933)(Visualization.calculateCentralAngle(bogota, london))
  }
}
