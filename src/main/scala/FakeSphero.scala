
class FakeSphero extends Sphero {
  def roll(velocity: Float, heading: Float) {
    println(s"roll: $velocity, heading: $heading")
  }

  def stop() {
    println("stop")
  }

  def beginCalibration() {
    println("beginCalibration")
  }

  def acceptCalibration() {
    println("acceptCalibration")
  }

  def setColor(r: Int, g: Int, b: Int) {
    println(s"setColor: $r $g $b")
  }

  def disconnect() {
    println("disconnect")
  }
}
