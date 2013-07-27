import com.leapmotion.leap.{Controller, Listener}
import se.nicklasgavelin.sphero.command.RollCommand

class LeapListener extends Listener {
  override def onFrame(leap: Controller) {
    val hand = leap.frame.hands.rightmost()
    if (hand != null) {
       println(s"Vector: ${hand.palmNormal}")
    }
  }
}