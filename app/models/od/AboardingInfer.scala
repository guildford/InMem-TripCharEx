package models.od

import models.caches
import models.caches.DataPool
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by GUILDFORD on 2014/7/28.
 */
object AboardingInfer {

  def main(args: Array[String]) {
    normalization("public/data/BusCard/821")
  }

  def normalization(busFile : String)  {
//    if (DataPool.sc.isEmpty) DataPool.init
//
//    val start = System.currentTimeMillis()
////    println(logData.count())
//    val end = System.currentTimeMillis()
//
//    val took = (end - start).toDouble / 1000
//    println("took = " + took)
  }

}
