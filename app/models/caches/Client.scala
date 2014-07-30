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

    var tp = ()
    tp += "2"
    println("hah")

  }

  def PlayMain() {

    val path: String = "public/data/BusCard/sample"
    val des: DataDescription = new DataDescription(path, 731)

    start = System.currentTimeMillis()
    DataPool.put(des)
    printDuration(start)

    start = System.currentTimeMillis()
    val data: Option[RDD[String]] = DataPool.get(des)
    println(data.get.count())
    printDuration(start)

  }


}