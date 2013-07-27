import java.util
import java.util.concurrent.CountDownLatch
import se.nicklasgavelin.bluetooth.Bluetooth.EVENT
import se.nicklasgavelin.bluetooth.{BluetoothDiscoveryListener, BluetoothDevice, Bluetooth}
import se.nicklasgavelin.sphero.exception.{RobotBluetoothException, InvalidRobotAddressException}
import se.nicklasgavelin.sphero.Robot

class SpheroController(robot: Robot) extends Sphero {

  def roll(velocity: Float, heading: Float) {
    robot.roll(heading, velocity)
  }

  def stop() {
    robot.stopMotors()
  }

  def beginCalibration() {
    robot.setRotationRate(0.1.toFloat)
    robot.rotate((robot.getRobotMovement().getHeading+170)%360)
  }

  def acceptCalibration() {
    robot.calibrate(robot.getRobotMovement().getHeading)
    robot.setRotationRate(1)
  }

  def setColor(r: Int, g: Int, b: Int) {
    robot.setRGBLEDColor(r, g, b)
  }
}

object SpheroController {

  def connect() = {
    val holder = new RobotHolder
    val bt = new Bluetooth(new RobotBtListener(holder), Bluetooth.SERIAL_COM)
    bt.discover()
    val robot = holder.awaitRobot()
    new SpheroController(robot)
  }

  private class RobotHolder {
    private var robot: Option[Robot] = None
    private val foundSignal = new CountDownLatch(1)

    def awaitRobot() = {
      foundSignal.await()
      robot.getOrElse(throw new IllegalStateException("Didn't find robot in the robot holder!"))
    }

    def storeRobot(r: Robot) {
      robot = Some(r)
      foundSignal.countDown()
    }
  }

  private class RobotBtListener(holder: RobotHolder) extends BluetoothDiscoveryListener {
    def deviceSearchCompleted(devices: util.Collection[BluetoothDevice]) {
      println("Bluetooth search completed.")
    }

    def deviceDiscovered(device: BluetoothDevice) {
      if (Robot.isValidDevice(device)) {
        println(s"Found robot ${device.getAddress}")
        createRobot(device, holder)
      }
    }

    def deviceSearchFailed(p1: EVENT) {
      println("Error message: "+p1.getErrorMessage)
    }

    def deviceSearchStarted() {
      println("Searching for Bluetooth devices...")
    }
  }

  private def createRobot(device: BluetoothDevice, holder: RobotHolder) {
    try {
      val r = new Robot(device)
      if (r.connect()) {
        println(s"Connected to ${device.getName} : ${device.getAddress}!")
        holder.storeRobot(r)
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

}