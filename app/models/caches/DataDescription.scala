package models.caches

import scala.collection.mutable

/**
 * Created by GUILDFORD on 2014/7/29.
 */
abstract class DataDescription {
  val path : String
  //  val date : Int
  val loadMoment : Long
}

case class BusCard(p : String, d : Int) extends DataDescription{
//class DataDescription(val path: String, val date: Int) {
  val path : String = p
  val date : Int = d
  val loadMoment: Long = System.currentTimeMillis()
}

case object BusCard {

  val originFormat: String = ""

  //  val fields: mutable.HashMap[Int, String] = mutable.HashMap(
  //    2 -> "TimeStamp",
  //    6 -> "CardId",
  //    9 -> "LineId",
  //    10 -> "VehicleId",
  //    11 -> "AboardId",
  //    12 -> "DebusId")
  val fields: mutable.HashMap[String, Int] = mutable.HashMap(
    "TimeStamp" -> 2,
    "CardId" -> 6,
    "LineId" -> 9,
    "VehicleId" -> 10,
    "AboardId" -> 11,
    "DebusId" -> 12)

  //  val frequently: Array[Int] = Array(2, 6, 9, 10, 11, 12)
  val frequently: Array[String] = Array("TimeStamp", "CardId", "LineId", "VehicleId", "AboardId", "DebusId")
}


case class FileAccessLog(p: String) extends DataDescription {
  val path: String = p
  val loadMoment: Long = System.currentTimeMillis()
}