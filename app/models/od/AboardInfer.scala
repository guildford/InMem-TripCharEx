package models.od

import models.caches.{BusCard, DataDescription, DataPool}

/**
 * Created by GUILDFORD on 2014/7/28.
 */
object AboardInfer {

  def main(args: Array[String]) {

    // 638 route sample
    val path: String = "public/data/BusCard/sample"
    val des: DataDescription = new DataDescription(path, 731)

    DataPool.put(des)
    aboardTime(des)

  }

  def aboardTime(des: DataDescription) {
    val option = DataPool.get(des)
    for (data <- option) {
      // TODO for all DBR-routes
      val cleaned = data.map(line => {
        val s = line.split(",")
        var tp = ("")
        for (label <- BusCard.frequently) {
          val option = BusCard.fields.get(label)
          for (t <- option) {
            tp += s(t)
          }
        }
        tp
      })
      cleaned.take(10).foreach(println)
    }
  }

  def extractFrequentlyUsedFields(line: String) = {

  }

}
