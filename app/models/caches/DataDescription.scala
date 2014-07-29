package models.caches

import scala.collection.mutable

/**
 * Created by GUILDFORD on 2014/7/29.
 */
abstract class DataDescription
case class DistanceBasedRate(
  path : String,
  detail : String,
  date : Long,
  originFormat : String,
  fields : mutable.HashMap[Int, String],
  frequently : Array[Int],
  loadMoment : Long) extends DataDescription{

}