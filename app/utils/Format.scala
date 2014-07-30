package utils

/**
 * Created by GUILDFORD on 2014/7/30.
 */
object Format {

  def stamp2seconds(s : String) : Int = {
    val h : Int = s.substring(8, 10).toInt
    val m : Int = s.substring(10, 12).toInt
    val sec : Int = s.substring(12).toInt
    3600 * h + 60 * m + sec
  }

}
