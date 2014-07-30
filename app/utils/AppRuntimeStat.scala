package utils

/**
 * Created by Guildford on 2014/7/30.
 */
object AppRuntimeStat {

  def printDuration(start: Long) {
    println("took " + (System.currentTimeMillis() - start).toDouble / 1000 + " s.")
  }

}
