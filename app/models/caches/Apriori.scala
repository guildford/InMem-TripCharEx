package models.caches

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._

/**
 * Created by GUILDFORD on 2014/8/4.
 */

case class ItemCount(item: String, count: Int)
class Apriori {

  var data : RDD[String] = _

  def readData(path : String) = {
    data = DataPool.sc.textFile(path)
    val a = data.flatMap(_.split(" ")).map(item => (item, 1)).reduceByKey(_ + _).map(ic => ItemCount(ic._1, ic._2))
    a.filter(ic => ic.count > 10000).foreach(println)
  }

}

object Apriori {

  def main(args: Array[String]) {
    val test = new Apriori()
    test.readData("public/data/log/retail.dat")
  }

}