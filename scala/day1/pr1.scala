/*
def asd {
    var i = 1
    while(i<= 3) {
         println("ja")
         i += 1
    }
    for (i <- 0 until args.length){
         println(args(i))
    }
}

asd
*/
class Compass{
  val directions = List("N", "E", "S", "W")
  var bearing = 0
  println("Initial bearing: ")
  println(direction)
  
  def direction() = directions(bearing)

  def inform(turndirection: String){
    println("Turning " + turndirection + ". Now bearing " + direction)
  }

  def turnRight(){
    bearing = (bearing + 1) % directions.size
    inform("R")
  }

  def turnLeft() {
    bearing = (bearing + (directions.size - 1)) % directions.size
    inform("L")
  
  }


}

val myCompass = new Compass

myCompass.turnRight
myCompass.turnRight

myCompass.turnLeft
myCompass.turnLeft
myCompass.turnLeft



