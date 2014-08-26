package net.ikenna

import org.json4s.jackson.JsonMethods._
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import scalaj.http.Http
import java.net.{URLEncoder, URI}

object Question {

  def ask(question: String): String = {
    if (question.startsWith("How old is"))
      getAge(question)
    else if (question.startsWith("What is the birth place of"))
      getBirthPlace(question)
    else "Unknown question"
  }

  def getAge(question: String): String = {
    val name = getName(question, "How old is")
    val result = httpRequest(s"dbres:${name}", "dbpedia-owl:birthDate")
    val birthDay = parseBirthday(getJsonValue(result))
    (DateTime.now().getYear - birthDay.getYear).toString
  }

  def getBirthPlace(question: String): String = {
    val name = getName(question, "What is the birth place of")
    val result = httpRequest(s"dbres:${name}", "dbpprop:birthPlace")
    getJsonValue(result)
  }

  def parseBirthday(input: String) = DateTimeFormat.forPattern("yyyy-MM-dd+hh:mm").parseDateTime(input)

  def getJsonValue(input: String) = compact(render(parse(input) \\ "value")).replace("\"", "")

  def httpRequest(name: String, property: String): String = {
    val dbpediaBaseUrl = new URI("http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=").toASCIIString
    val suffix = "&format=application%2Fsparql-results%2Bjson&timeout=30000&debug=on"
    val url = dbpediaBaseUrl + sparqlQuery(name, property) + suffix
    Http(url).asString
  }

  def getName(input: String, splitString: String): String = input.split(splitString)(1).trim.replace(" ", "_").replace("?", "")

  def sparqlQuery(resource: String, queryType: String): String =
    URLEncoder.encode( s"""PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>
      |PREFIX dbpprop: <http://dbpedia.org/property/>
      |PREFIX dbres: <http://dbpedia.org/resource/>
      |
      |SELECT ?y WHERE {
      | ${resource} ${queryType} ?y .
      | }
      |
      |limit 10
    """.stripMargin, "UTF-8")
}
