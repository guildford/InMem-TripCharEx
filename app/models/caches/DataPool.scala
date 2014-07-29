package models.caches

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * Created by GUILDFORD on 2014/7/29.
 */
object DataPool {

  val conf : SparkConf = new SparkConf(false) // skip loading external settings
    .setMaster("local[8]") // run locally with enough threads
    .setAppName("firstSparkApp")
    .set("spark.logConf", "true")
    .set("spark.driver.host", "localhost")

  val sc : SparkContext = new SparkContext(conf)
  val dataTable: mutable.HashMap[DataDescription, RDD[String]] = new mutable.HashMap[DataDescription, RDD[String]]()
  //  val stat : Statistics
//  val scheduler : Scheduler
  val totalCapacity : Double = 8
  val loadFactor : Double = 0.5

  def get(data: DataDescription): Option[RDD[String]] = {
    dataTable.get(data)
  }

  def put(des: DataDescription): Boolean = {
    var rdd: RDD[String] = sc.textFile(des.path, 8).cache()
    rdd.count()
    dataTable.put(des, rdd)

    true
  }

}
