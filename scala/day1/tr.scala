class Person(val name:String)
trait Nice {
def greet() = println("Howdily doodily." )
}
class Character(val name:String)
val flanders = new Character("Ned" ) with Nice
flanders.greet
