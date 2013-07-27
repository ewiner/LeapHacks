import com.leapmotion.leap.{Controller, Listener, Vector}
import se.nicklasgavelin.sphero.command.RollCommand

class LeapListener(sphero: Sphero) extends Listener {

  override def onFrame(leap: Controller) {
    val hand = leap.frame.hands.rightmost()
    if (hand != null) {
      val normal = hand.palmNormal
      val y = Vector.yAxis()

      val projection = normal minus (y times (normal dot y))
      val magnitude = projection.magnitude
      val angle = (if (normal.roll > 0)
        projection angleTo (Vector.zAxis opposite)
      else math.Pi + (math.Pi - (projection angleTo (Vector.zAxis opposite)))).toDegrees.toFloat

      sphero.roll(magnitude,angle)
    }
  }
}