import com.leapmotion.leap.Controller

object Program {

  def main(args: Array[String]) {
    println("Hello!  Connecting to Sphero...")
    val sphero: Sphero = SpheroController.connect().getOrElse{
      println("No sphero found!")
      new FakeSphero
    }

    calibrate(sphero)
    println("Connected to Sphero!  Connecting to Leap...")

    val leapController = new Controller()
    //TODO: check if leap is actually connected
    val listener = new LeapListener(sphero)
    leapController.addListener(listener)
    readLine("Press enter to exit.")

    sphero.disconnect()
  }

  def calibrate(controller: Sphero) {
    println("Calibrating")
  }
}
