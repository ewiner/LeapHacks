import scala.collection.mutable
import se.nicklasgavelin.bluetooth.Bluetooth.EVENT
import se.nicklasgavelin.bluetooth.{BluetoothDiscoveryListener, BluetoothDevice, Bluetooth}
import se.nicklasgavelin.sphero.command.{CommandMessage, FrontLEDCommand, RollCommand}
import se.nicklasgavelin.sphero.example.Example_Site_API
import se.nicklasgavelin.sphero.exception.{RobotBluetoothException, InvalidRobotAddressException}
import se.nicklasgavelin.sphero.response.{InformationResponseMessage, ResponseMessage}
import se.nicklasgavelin.sphero.RobotListener.EVENT_CODE
import se.nicklasgavelin.sphero.{Robot, RobotListener}

/**
 * Created with IntelliJ IDEA.
 * User: Joseph
 * Date: 7/26/13
 * Time: 8:49 PM
 * To change this template use File | Settings | File Templates.
 */

object SpheroTest {
   def main (args: Array[String]) {
    val example_Site_API:Example_Site_API  = new Example_Site_API()
    val serialVersionUID:Long = 6998786554264771793L
    val responses:Int = 0
    val ct = new ConnectThread
    ct.run()
  }

  def getRobot():Robot = {
    val ct = new ConnectThread
    ct.run()
    while(ct.robot == null){
      Thread.sleep(100)
    }
    return ct.robot
  }
}

class ConnectThread extends BluetoothDiscoveryListener with RobotListener {

  var bt:Bluetooth = new Bluetooth( this, Bluetooth.SERIAL_COM )
  var robot:Robot = null

  def run(){
    this.bt.discover()
  }

  def deviceSearchCompleted(devices: java.util.Collection[se.nicklasgavelin.bluetooth.BluetoothDevice]) {
    import scala.collection.JavaConverters._

    val robots:mutable.Buffer[Robot] = mutable.Buffer[Robot]()
    println("Completed device discovery")
    for (d <- devices.asScala) {
      if (Robot.isValidDevice(d)) {
        println("Found robot " + d.getAddress)
        try {
          val r: Robot = new Robot(d)
          r.addListener(this)
          if (r.connect) {
            robots += r
            println(s"Connected to ${d.getName} : ${d.getAddress}")
            r.rgbTransition(255, 0, 0, 0, 255, 255, 50)
            r.sendCommand(new FrontLEDCommand(1))
            robot = r
          }
          else println("Failed to connect to robot")
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
  }

  def responseReceived(p1: Robot, p2: ResponseMessage, p3: CommandMessage) {}

  def event(p1: Robot, p2: EVENT_CODE) {}

  def informationResponseReceived(p1: Robot, p2: InformationResponseMessage) {}

  def deviceDiscovered(p1: BluetoothDevice) {}

  def deviceSearchFailed(p1: EVENT) {}

  def deviceSearchStarted() {}
}
