import com.leapmotion.leap.{Controller, Listener}

class LeapListener extends Listener {
  override def onFrame(p1: Controller) {
    val fings = p1.frame().fingers().count()
    println(s"Fingers: $fings")
  }
}