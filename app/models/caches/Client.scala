package models.caches

import org.apache.spark.rdd.RDD
import utils.AppRuntimeStat._

/**
 * Created by GUILDFORD on 2014/7/29.
 */
class Client {

}

object Client {

  var start: Long = 0
  var end: Long = 0

  def main(args: Array[String]) {

    val path: String = "public/data/BusCard/sample"
    val des : BusCard= new BusCard(path, 821)
    DataPool.put(des)
    DataPool.loadAll
    DataPool.get(des) match {
      case Some(rdd) => {
        rdd.take(10).foreach(println)
        val start = System.currentTimeMillis()
        println(rdd.count())
        printDuration(start)
      }
      case None =>
    }
//    // extract particular route
//    DataPool.sc.textFile("public/data/BusCard/821")
//      .filter(line => {
//      val s = line.split(",")
//      if (s(9).equalsIgnoreCase("638")) true else false
//    })
//      .saveAsTextFile("public/data/BusCard/sample")

  }

  def PlayMain() {

//    val path: String = "public/data/BusCard/sample"
//    val des: DataDescription = new BusCard(path, 731)
//
//    start = System.currentTimeMillis()
//    DataPool.put(des)
//    printDuration(start)
//
//    start = System.currentTimeMillis()
//    val data: Option[RDD[String]] = DataPool.get(des)
//    println(data.get.count())
//    printDuration(start)
    Client.main(Array())

  }


}