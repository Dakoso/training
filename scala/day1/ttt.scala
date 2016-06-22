object Board{
    var table = List(List(" ", " ", " "), List(" ", " ", " "), List(" ", " ", " "))
    //val table = List(List("O", "X", "O"), List("X", "O", "X"), List("O", "X", "O"))
    //val table = List(List("X", "X", "X"), List("X", "O", "O"), List("O", "X", "O"))
    //val table = List(List("O", "X", "O"), List("X", "X", "O"), List("O", "X", "X"))
    //val table = List(List("O", " ", "X"), List(" ", "X", "X"), List("X", " ", "O"))
    //val table = List(List(" ", "X", " "), List(" ", "O", " "), List(" ", "X", "O"))

    // START WITH O
    var lastMove = "X"



    def visual() {
        println("  _0_ _1_ _2_ ")
        println("  ___ ___ ___ ")
        print("\n")
        var count = 0
        table.foreach{ file =>
            print(count + "|")
            file.foreach{ element =>
                print(" " + element + " |" )
            }
            print("\n")
            print("  ___ ___ ___ ")
            print("\n")
            print("\n")
            count += 1
        }
    }

    def checkNWin(letter: String):Boolean = {

        // ROW
        table.foreach{ row =>
            var count = 0
            row.foreach{ element =>
                if (element == letter){
                    count += 1
                }
            }
            if (count == 3){
                return true 
            }
        }

        // COLUM

        var column = 0
        
        for (column <- 0 until 3){
            var count = 0
            table.foreach{ row =>
                if (row(column) == letter){
                    count += 1
                }
            }
            if (count == 3){
                return true
            }
        }

        // DIAGONAL
        var count = 0
        for (i <- 0 until 3){
            if (table(i)(i) == letter){
                count += 1
            }
        }
        if (count == 3){
            return true
        }
        
        count = 0
        var i = 0 
        var j = 2
        for (_ <- (0 to 2)){

            if (table(i)(j) == letter){
                count += 1
            }
            i += 1
            j -= 1
        }
        if (count == 3){
            return true
        }

        return false
    }
    def isComplete():Boolean = {
        table.foreach{ row =>
            row.foreach{ element =>
                if (element == " "){
                    return false
                }
            }
        }
        return true
    }

    def checkWin(): Boolean = {

        if (checkNWin("O")){
            println("O wins")
            return false
        }
        else if (checkNWin("X")) {
            println("X wins")
            return false
        }
        else if (isComplete()){
            println("Tie")
            return false
        }
        else {
            println("No Winner Yet")
            return true
        }

    }
    def moveLetter(x:Int, y:Int, letter:String):Boolean = {
        if (table(x)(y) != " "){
            println("ERROR, no puedes mover ahi! Mueve otra vez, jugador " + letter)
            return false
        }
        table = table.updated(x, table(x).updated(y, letter))
        Board.visual()
        return true
    }

    def moveO(x:Int, y:Int): Boolean = {
        return moveLetter(x, y, "O")
    }

    def moveX(x:Int, y:Int): Boolean = {
        return moveLetter(x, y, "X")
    }

    def move(x:Int, y:Int): Boolean = {
        if (lastMove == "X"){
            if(!(moveO(x, y))){
                return true
            }
            lastMove = "O"
        }
        else if (lastMove == "O"){
            if(!(moveX(x, y))){
                return true
            }
            lastMove = "X"
        }
        if (!checkWin()){
            return false
        }
        return true
    
    }
    def parseMove(line:String): Boolean = {
        println("Has movido en " + line)
        //var line_arr
        //var x
        //var y
        //try{
        //    line_arr = line.split(" ")
        //    x = line_arr(0).toInt
        //    y = line_arr(1).toInt
        //}
        //catch{
        //    case e: Exception =>
        //        println("Something Wrong, type again " + e)
        //    return true
        //}
        var line_arr = line.split(" ")
        var x = line_arr(0).toInt
        var y = line_arr(1).toInt

        if (x < 0 || x >= 3 || y < 0 || y >= 3){
            println("Esa casilla no existe")
            return true
        }

        return move(x, y)
    }

}
Board.visual()
//Board.moveO(0,0)
//Board.moveX(0,1)
//Board.moveO(1,1)
//Board.moveO(1,1)
//Board.moveX(2,0)
//Board.moveO(2,2)
/*
Board.move(0,0)
Board.move(0,1)
Board.move(1,1)
Board.move(1,1)
Board.move(2,0)
Board.move(2,2)
*/

var pj = "O"
println("Player " + pj + " move:")
while (Board.parseMove(readLine())){
    if (pj == "O"){
        pj = "X"
    }
    else{
        pj = "O"
    }
    println("Player " + pj + " move:")
}
