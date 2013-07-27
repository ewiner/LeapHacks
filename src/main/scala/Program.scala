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
    if (leapController.isConnected) {
      val listener = new LeapListener(sphero)
      leapController.addListener(listener)
      readLine("Press enter to exit.")
      leapController.removeListener(listener)
    } else {
      println("Leap not connected...")
      readLine("Press enter to roll a little, then exit.")
      sphero.roll(1, 0)
      Thread.sleep(500)
    }
    sphero.disconnect()
  }

  def calibrate(controller: Sphero) {
    println("Calibrating")
  }

  // sudo ln -sfh OLD Current
}
