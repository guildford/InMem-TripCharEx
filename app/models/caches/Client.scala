package models.caches

import org.apache.spark.rdd.RDD

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
    val des: DataDescription = new DataDescription(path, 731)

    start = System.currentTimeMillis()
    DataPool.put(des)
    end = System.currentTimeMillis()
    printDuration

    start = System.currentTimeMillis()
    val data: Option[RDD[String]] = DataPool.get(des)
    println(data.get.count())
    end = System.currentTimeMillis()
    printDuration

  }

  def PlayMain() {

    val path: String = "public/data/BusCard/sample"
    val des: DataDescription = new DataDescription(path, 731)

    start = System.currentTimeMillis()
    DataPool.put(des)
    end = System.currentTimeMillis()
    printDuration

    start = System.currentTimeMillis()
    val data: Option[RDD[String]] = DataPool.get(des)
    println(data.get.count())
    end = System.currentTimeMillis()
    printDuration

  }

  def printDuration {
    println("took " + (end.toDouble - start.toDouble) / 1000 + " s.")
  }
}