import com.leapmotion.leap.{Listener, Controller}
import se.nicklasgavelin.bluetooth.Bluetooth

object Program {
  val listener = new LeapListener

  def main(args: Array[String]) {
    val leapController = new Controller()
    leapController.addListener(listener)
    readLine("Press enter to exit.")
  }

}
