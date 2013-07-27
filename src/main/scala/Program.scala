import com.leapmotion.leap.{Listener, Controller}
import se.nicklasgavelin.bluetooth.Bluetooth

object Program {
  def main(args: Array[String]) {
    val sphero = SpheroController.connect()
    val leapController = new Controller()
    leapController.addListener(new LeapListener(sphero))
    readLine("Press enter to exit.")
  }

}
