import scala.io._
import scala.actors._
import Actor._

// START:loader
object PageLoader {
 def getPageSize(url : String): Int = {
    //print("Getting size of " + url)
    var size = 0
    try {
        size = Source.fromURL(url).mkString.length
    } catch {
        case e: Exception => {}
        
    }
    return size
 }
 def getNumberLinks(url: String): Int = {
    val url_source = Source.fromURL(url).mkString
    val ref = """<a href=\"([^\"]*)\"""".r
    val links = ref.findAllIn(url_source)
    //links.foreach(link => println(link + '\n'))
    //for(m <- links) println(m)
    return links.size
 }
 def getLinksSize(url: String): Int = {
    val url_source = Source.fromURL(url).mkString
    val ref = "<a href=\"([^\"]*)\"".r
    val links = ref.findAllIn(url_source)
    val link_url_reg = "\"(.*)\"".r
    var count = PageLoader.getPageSize(url)
    links.foreach{ link =>
        val link_url = link_url_reg.findFirstIn(link)
        //println(link_url)
        for(m <- link_url){
            var stripped_url = m.stripPrefix("\"").stripSuffix("\"")
            if (!stripped_url.startsWith("http")){
                stripped_url = url + stripped_url
            }
            count += PageLoader.getPageSize(stripped_url)
        }
    }

        
    return count
 }
}
// END:loader

val urls = List(
               "http://www.elandroidelibre.com/",
               "http://www.elmundo.es/",
               "http://www.google.com/",
               "http://www.cnn.com/"
           )

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
// START:sequential
def getLinksSizeSequentially() = {
 for(url <- urls) {
   println("Size for " + url + ": " + PageLoader.getLinksSize(url))
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

// START:concurrent
def getLinksSizeConcurrently() = {
 val caller = self

 for(url <- urls) {
   actor { caller ! (url, PageLoader.getLinksSize(url)) }
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

println("Sequential run TOTAL SIZE:")
timeMethod { getLinksSizeSequentially }

println("Concurrent run")
timeMethod { getPageSizeConcurrently }
println("Concurrent run LINKS")
timeMethod { getNumberLinksConcurrently }
println("Concurrent run TOTAL SIZE")
timeMethod { getLinksSizeConcurrently }
// END:script
