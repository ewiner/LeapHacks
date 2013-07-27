import java.util
import se.nicklasgavelin.bluetooth.Bluetooth.EVENT
import se.nicklasgavelin.bluetooth.{BluetoothDiscoveryListener, BluetoothDevice, Bluetooth}
import se.nicklasgavelin.sphero.command.RollCommand
import se.nicklasgavelin.sphero.exception.{RobotBluetoothException, InvalidRobotAddressException}
import se.nicklasgavelin.sphero.Robot

class SpheroController extends Sphero {

  private var maybeRobot: Option[Robot] = None
  def robot = maybeRobot.getOrElse(throw new IllegalStateException("Robot not initialized!"))

  private val btListener = new BluetoothDiscoveryListener {
    def deviceSearchCompleted(devices: util.Collection[BluetoothDevice]) {
      println("Bluetooth search completed.")
    }

    def deviceDiscovered(device: BluetoothDevice) {
      if (Robot.isValidDevice(device)) {
        connect(device)
      }
    }

    def deviceSearchFailed(p1: EVENT) {}

    def deviceSearchStarted() {
      println("Searching for Bluetooth devices...")
    }
  }

  private def connect(device: BluetoothDevice) {
    println(s"Found robot ${device.getAddress}")
    try {
      val r = new Robot(device)
      if (r.connect()) {
        println(s"Connected to ${device.getName} : ${device.getAddress}!")
        maybeRobot = Some(r)
      } else {
        println("Failed to connect to robot!")
      }
    }
    catch {
      case ex: InvalidRobotAddressException => {
        println("InvalidRobotAddressException\n")
      }
      case ex: RobotBluetoothException => {
        println("RobotBluetoothException\n")
      }
    }
  }

  {
    val bt = new Bluetooth(btListener, Bluetooth.SERIAL_COM)
    bt.discover()
  }

  def roll(velocity: Float, heading: Float) {
    robot.roll(heading, velocity)
  }

  def stop() {
    robot.stopMotors()
  }

  def beginCalibration() {

  }

  def acceptCalibration() {

  }

  def setColor(r: Int, g: Int, b: Int) {
    robot.setRGBLEDColor(r, g, b)
  }
}