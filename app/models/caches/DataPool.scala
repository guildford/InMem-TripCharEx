package models.caches

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * Created by GUILDFORD on 2014/7/29.
 */
object DataPool {

  val conf: SparkConf = new SparkConf(false) // skip loading external settings
    .setMaster("local[8]") // run locally with enough threads
    .setAppName("firstSparkApp")
    .set("spark.logConf", "true")
    .set("spark.driver.host", "localhost")

  val sc: SparkContext = new SparkContext(conf)
  val dataTable: mutable.HashMap[DataDescription, RDD[String]] = new mutable.HashMap[DataDescription, RDD[String]]()
  //  val stat : Statistics
  //  val scheduler : Scheduler
  val totalCapacity: Double = 8
  val loadFactor: Double = 0.5

  def get(des: DataDescription): Option[RDD[String]] = {
    dataTable.get(des)
  }

  def put(des: DataDescription): Boolean = {
    dataTable.put(des, sc.textFile(des.path, 8))
    true
  }

  def loadAll {
    for (key <- dataTable.keySet) {
      key match {
        case BusCard(_,_) =>{
          val s = dataTable.get(key)  match {
            case Some(rdd) => {
//              println("###")
              val rddcache = rdd.map(line => {
                val s = line.split(",")
//                ("CardId"->s(BusCard.fields.getOrElse("CardId", -1)),
//                  "LineId"->s(BusCard.fields.getOrElse("LineId", -1)),
//                  "VehicleId"->s(BusCard.fields.getOrElse("VehicleId", -1)),
//                  "AboardId"->s(BusCard.fields.getOrElse("AboardId", -1)),
//                  "DebusId"->s(BusCard.fields.getOrElse("DebusId", -1)),
//                  "TimeStamp"->s(BusCard.fields.getOrElse("TimeStamp", -1)))
                s(BusCard.fields.getOrElse("CardId", -1)) + "," +
                  s(BusCard.fields.getOrElse("LineId", -1)) + "," +
                  s(BusCard.fields.getOrElse("VehicleId", -1)) + "," +
                  s(BusCard.fields.getOrElse("AboardId", -1)) + "," +
                  s(BusCard.fields.getOrElse("DebusId", -1)) + "," +
                  s(BusCard.fields.getOrElse("TimeStamp", -1))
              }).cache()
              rddcache.count()
              dataTable(key) = rddcache
            }
//            case None =>
          }
        }

        case FileAccessLog(p) => {
          dataTable.get(key) match {
            case Some(rdd) => {
              println("Caching file access logs : " + rdd.cache().count() + " transactions.")
            }
          }
        }
        //        case None =>
      }
    }
  }

}
