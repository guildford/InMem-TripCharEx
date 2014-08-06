package models.caches

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import utils.AppRuntimeStat._

/**
 * Created by GUILDFORD on 2014/8/4.
 */

case class ItemCount(item: String, count: Int)

class Apriori(support: Double) extends java.io.Serializable {

  var transaction: RDD[String] = null
  var dictionary: RDD[String] = null
  var tranCount: Long = 0

  def readData(des: DataDescription): Boolean = {
    val option = DataPool.get(des)
    option match {
      case Some(tran) => {
        transaction = tran
        tranCount = tran.count()
        dictionary = transaction.flatMap(_.split(" "))
        true
      }
      case None => false
    }
  }

  def frequent = {
    val L1 = dictionary
      .map(item => (item, 1))
      .reduceByKey(_ + _)
      .map(ic => ItemCount(ic._1, ic._2))
      .filter(ic => (ic.count.toDouble / tranCount) > support)
      .map(ic => Array(ic))
    //    L1.foreach(ic => println(ic.count.toDouble / tranCount + ", " + ic))

    val C2: RDD[Array[ItemCount]] = innerJoin(L1)
    C2.foreach(arr => {
      arr.foreach(ic => print(ic.item + " "))
      println()
    })
    //      .filter(ics => {
    //        ics.productIterator
    //        !ics._1.equals(ics._2) &&
    //        ics._1.item.toInt < ics._2.item.toInt})
    //      .map(ic => {((ic._1.item, ic._2.item), (ic._1, ic._2))})
    //    val C2_keys = C2.keys.collect()
    //    val L2 = C2_keys.filter({ic =>
    //      val twoCount = transaction.filter(line => {
    //        val s = line.split(" ")
    //        var a = 0
    //        var b = 0
    //        s.foreach(record => {
    //          if (record.equals(ic._1)) {a = 1}
    //          if (record.equals(ic._2)) {b = 1}
    //        })
    //        a > 0 && b > 0
    //      }).count()
    //      (twoCount.toDouble / tranCount) > support
    //    })
    //    L2.foreach(println)

    val C3 = innerJoin(C2)
    C3.foreach(arr => {
      arr.foreach(ic => print(ic.item + " "))
      println()
    })
  }

  def innerJoin(L: RDD[Array[ItemCount]]): RDD[Array[ItemCount]] = {
    val joined = L.cartesian(L)
    joined
      // 判断是否可连接，如果两个Array前 n-1 个元素相同，且最后一个元素不同
      .filter(tp => {
      canInnerJoin(tp._1, tp._2)
    })
      // 实施具体连接，前 n-1 个元素保留，最后两个元素分别取自两个Array
      .map(tp => {
      val a = tp._1.last.item.toInt
      val b = tp._2.last.item.toInt
      var min: ItemCount = null
      var max: ItemCount = null
      if (a < b) {
        min = tp._1.last
        max = tp._2.last
      } else {
        min = tp._2.last
        max = tp._1.last
      }
      tp._1.dropRight(1) ++ Array(min) ++ Array(max)
    })
    // 过滤掉相同元素
    //      .groupBy(arr => {
    //        arr(0).item + arr(1).item
    //      })
    //      .map(tp => tp._2.head)
  }

  def canInnerJoin(arrayA: Array[ItemCount], arrayB: Array[ItemCount]): Boolean = {
    if (arrayA.length == 1) {
      if (arrayA(0).item.equals(arrayB(0).item)) {
        false
      } else {
        true
      }
    } else {
      // 判断前 n-1 个元素是否相等
      var headEq: Boolean = true
      for (index <- 0 until arrayA.length - 1) {
        if (!arrayA(index).item.equals(arrayB(index).item)) {
          headEq = false
        }
      }
      // 最后一个元素是否 不 相等
      val tailEq: Boolean = {
        arrayA.last.item.equals(arrayB.last.item)
      }

      headEq == true && tailEq == false
    }
  }

}

object Apriori {

  def main(args: Array[String]) {
    val test = new Apriori(0.056)
    val des = new FileAccessLog("public/data/log/retail.dat")
    DataPool.put(des)
    DataPool.loadAll

    val start = System.currentTimeMillis()
    test.readData(des)
    test.frequent
    printDuration(start)
  }

}