package models.caches

import scala.collection.mutable

/**
 * Created by GUILDFORD on 2014/7/29.
 */
//abstract class DataDescription {
//  val path : String
//  val date : Int
//  val loadMoment : Long
//}

//class BusCard(p : String, d : Int) extends DataDescription{
class DataDescription(val path: String, val date: Int) {
  val loadMoment: Long = System.currentTimeMillis()
}

object BusCard {

  val originFormat: String = ""

  val fields: mutable.HashMap[Int, String] = mutable.HashMap(
    2 -> "TimeStamp",
    6 -> "CardId",
    9 -> "LineId",
    10 -> "VehicleId",
    11 -> "AboardId",
    12 -> "DebusId")

  val frequently: Array[Int] = Array(2, 6, 9, 10, 11, 12)

}