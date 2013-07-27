import com.leapmotion.leap.{Controller, Listener, Vector}
import se.nicklasgavelin.sphero.command.RollCommand

class LeapListener(sphero: Sphero) extends Listener {

  override def onFrame(leap: Controller) {
    val hand = leap.frame.hands.rightmost()
    if (hand != null) {
      val normal = hand.palmNormal
      val y = Vector.yAxis()

      val projection = normal minus (y times (normal dot y))

      println(s"Magnitude, Angle: ${projection.magnitude}, ${projection angleTo Vector.xAxis}")
    }
  }
}