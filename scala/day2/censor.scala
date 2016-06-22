import scala.collection.mutable.HashMap

class Censor(val original_text:String){

    //var censor_words: Map[String, String] = Map()
    var censor_words = collection.mutable.Map[String, String]()


    def censurate(): String = {
        if (censor_words.size == 0){
            load_censor_words()
        }

        val text_list = original_text.split(" ")
        var censored_list = Array[String]()
        censored_list = text_list.map{word =>
            var lower_word = word.toLowerCase
            
            var has_comma = false
            if (lower_word.indexOf(',') == lower_word.size - 1){
                has_comma = true
                lower_word = lower_word.split(',')(0)
            }
            var new_word = censor_words.getOrElse(lower_word, "__no_word__")
            if (new_word != "__no_word__"){
                if (has_comma){
                    new_word += ','
                }
            }
            else {
                new_word = word
            }
            new_word
        }
        return censored_list mkString (" ")
    }

    def load_censor_words(){
        println("loading default words")
        censor_words += ("shoot" -> "pucky")
        censor_words += ("darn" -> "beans")
    }

    def load_censor_words(path: String){

        val source = scala.io.Source.fromFile(path)

        source.getLines.foreach{ line =>
            val Array(key, value) = line.split(" ")
            censor_words += (key -> value)
        }
    }
}


val test_text = "Hola, que tal voy a cambiar shoot por pucky y esto no va a tener ningun sentido porque darn es beans, al igual que Darn, daRn y shoOt"

val my_censor = new Censor(test_text)
my_censor.load_censor_words("./censor_words.txt")
println(my_censor.censurate())

