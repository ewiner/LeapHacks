import com.leapmotion.leap.{Controller, Listener}

class LeapListener(sphero: Sphero) extends Listener {

  override def onFrame(leap: Controller) {
    val hand = leap.frame.hands.rightmost()
    if (hand != null) {
       println(s"Vector: ${hand.palmNormal}")
    }
  }
}