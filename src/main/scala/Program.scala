import com.leapmotion.leap.{Listener, Controller}
import se.nicklasgavelin.bluetooth.Bluetooth

object Program {



  def main(args: Array[String]) {
    println("Hello!  Connecting to Sphero...")
    val sphero: Sphero = SpheroController.connect()
    calibrate(sphero)
    println("Connected to Sphero!  Connecting to Leap...")
    val leapController = new Controller()
    val listener = new LeapListener(sphero)
    leapController.addListener(listener)
    readLine("Press enter to exit.")
  }

  def calibrate(controller: Sphero) {
  }
}
