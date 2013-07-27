trait Sphero {
  def roll(velocity: Float, heading: Float)
  def stop()
  def beginCalibration()
  def acceptCalibration()
  def setColor(r:Int, g:Int, b:Int)
  def disconnect()
}