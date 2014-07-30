package models.od

import models.caches.{BusCard, DataDescription, DataPool}
import utils.Format._
import utils.AppRuntimeStat._

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
    aboardTime(des)
    printDuration(start)

  }

  def aboardTime(des: DataDescription) {
    val option = DataPool.get(des)
    for (data <- option) {
      // TODO for all DBR-routes
      val cleaned = data.keyBy(line => {
        val s = line.split(",")
        (s(1),s(2),stamp2seconds(s(5)))
      })
      cleaned.take(10).foreach(println)
    }
  }

  def extractFrequentlyUsedFields(line: String) = {

  }

}
