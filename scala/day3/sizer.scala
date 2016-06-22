import scala.io._
import scala.actors._
import Actor._

// START:loader
object PageLoader {
 def getPageSize(url : String) = Source.fromURL(url).mkString.length
 def getNumberLinks(url: String): Int = {
    val url_source = Source.fromURL(url).mkString
    val ref = "<a href".r
    val links = ref.findAllIn(url_source)
    var count = 0
    return links.size

 }
}
// END:loader

/*
val urls = List("http://www.amazon.com/", 
               "http://www.twitter.com/",
               "http://www.google.com/",
               "http://www.cnn.com/" )
*/

val urls = List(
               "http://www.elandroidelibre.com/", 
               "http://www.elmundo.es/",
               "http://www.google.com/",
               "http://www.cnn.com/" )

// START:time
def timeMethod(method: () => Unit) = {
 val start = System.nanoTime
 method()
 val end = System.nanoTime
 println("Method took " + (end - start)/1000000000.0 + " seconds.")
}
// END:time

// START:sequential
def getPageSizeSequentially() = {
 for(url <- urls) {
   println("Size for " + url + ": " + PageLoader.getPageSize(url))
 }
}
// END:sequential

// START:sequential
def getNumberLinksSequentially() = {
 for(url <- urls) {
   println("Size for " + url + ": " + PageLoader.getNumberLinks(url))
 }
}
// END:sequential

// START:concurrent
def getPageSizeConcurrently() = {
 val caller = self

 for(url <- urls) {
   actor { caller ! (url, PageLoader.getPageSize(url)) }
 }

 for(i <- 1 to urls.size) {
   receive {
     case (url, size) =>
       println("Size for " + url + ": " + size)            
   }
 }
}
// END:concurrent

// START:concurrent
def getNumberLinksConcurrently() = {
 val caller = self

 for(url <- urls) {
   actor { caller ! (url, PageLoader.getNumberLinks(url)) }
 }

 for(i <- 1 to urls.size) {
   receive {
     case (url, size) =>
       println("Size for " + url + ": " + size)            
   }
 }
}
// END:concurrent

// START:script

println("Sequential run:")
timeMethod { getPageSizeSequentially }

println("Sequential run LINKS:")
timeMethod { getNumberLinksSequentially }

println("Concurrent run")
timeMethod { getPageSizeConcurrently }
println("Concurrent run LINKS")
timeMethod { getNumberLinksConcurrently }
// END:script
