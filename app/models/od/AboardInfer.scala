package models.od

import examples.WordCount
import models.caches.{BusCard, DataDescription, DataPool}
import utils.AppRuntimeStat._
import utils.Format._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

/**
 * Created by GUILDFORD on 2014/7/28.
 */
object AboardInfer {

  def main(args: Array[String]) {

    // 638 route sample
    val path: String = "public/data/BusCard/sample"
    val des: BusCard = new BusCard(path, 820)

    DataPool.put(des)
    DataPool.loadAll

    val start = System.currentTimeMillis()
//    aboardTime(des)
    wordCount
    printDuration(start)

  }

  def aboardTime(des: DataDescription) {
    val option = DataPool.get(des)
    for (data <- option) {
      // TODO for all DBR-routes
      val base = data.keyBy(line => {
        val s = line.split(",")
        // 线路号、车辆号、秒数（以30秒为间隔）作为key
        (s(1),s(2),stamp2seconds(s(5)) / 60)
      })

      val plus = base.map(tp => {
        ((tp._1._1, tp._1._2, tp._1._3 + 1), tp._2)
      })


//      base.take(10).foreach(println)
      println(base.count())

      // 测试在每个时间间隔阈值下，刷卡记录的聚类情况
//      for (step <- 5 to(300, 5)) {
//        val base = data.groupBy(line => {
//          val s = line.split(",")
//          // 线路号、车辆号、秒数（以30秒为间隔）作为key
//          (s(1), s(2), stamp2seconds(s(5)) / step)
//        })
//
//        //        base.take(10).foreach(println)
//        println(base.count())
//      }
    }
  }

  def wordCount = {
    val words = DataPool.sc.textFile("public/data/README.md").flatMap(_.split(" "))
    val heh = words.map(word => (word, 1)).reduceByKey(_ + _)
      .map(wc => WordCount(wc._1, wc._2))
    heh.foreach(println)
  }

}
