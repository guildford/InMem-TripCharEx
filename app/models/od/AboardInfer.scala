package models.od

import models.caches.{BusCard, DataDescription, DataPool}
import utils.AppRuntimeStat._
import utils.Format._

/**
 * Created by GUILDFORD on 2014/7/28.
 */
object AboardInfer {

  def main(args: Array[String]) {

    // 638 route sample
    val path: String = "public/data/BusCard/sample"
    val des: BusCard = new BusCard(path, 731)

    DataPool.put(des)
    DataPool.loadAll

    val start = System.currentTimeMillis()
    aboardTime(des)
    printDuration(start)

  }

  def aboardTime(des: DataDescription) {
    val option = DataPool.get(des)
    for (data <- option) {
      // TODO for all DBR-routes
      //      val cleaned = data.groupBy(line => {
      //        val s = line.split(",")
      //        // 线路号、车辆号、秒数（以30秒为间隔）作为key
      //        (s(1),s(2),stamp2seconds(s(5)) / 120)
      //      })
      //
      //      cleaned.take(10).foreach(println)
      //      println(cleaned.count())

      // 测试在每个时间间隔阈值下，刷卡记录的聚类情况
      for (step <- 20 to(600, 20)) {
        val cleaned = data.groupBy(line => {
          val s = line.split(",")
          // 线路号、车辆号、秒数（以30秒为间隔）作为key
          (s(1), s(2), stamp2seconds(s(5)) / step)
        })

        //        cleaned.take(10).foreach(println)
        println(cleaned.count())
      }
    }
  }

  def extractFrequentlyUsedFields(line: String) = {

  }

}
