package com.ruoze.business

import com.ruoze.Utils.`trait`.MyLoggin
import com.ruoze.Utils.{EmpParse}
import org.apache.spark.sql.{SparkSession}

/***
  * 清洗EMP文件数据
  */
object LogAnalysis extends MyLoggin {

  def main(args: Array[String]): Unit = {

    val arg = new LogAnalysisArguments(args)
    val spark = SparkSession
      .builder()
      .appName(arg.appName)
      .master(arg.master)
      .getOrCreate()

    // 是否兼容异常数据
    val isCompatibleExceptionData= spark.sparkContext.broadcast(arg.isCompatibleExceptionData)

    //import spark.implicits._
    val empRDD = spark.sparkContext.textFile(arg.fileName)

    val rowRDD = empRDD
      .mapPartitions(its =>
        for (it <- its) yield EmpParse.paresContent(it, isCompatibleExceptionData.value))
      .filter(_ != EmpParse.errorRow)

    val empDF = spark.createDataFrame(rowRDD, EmpParse.struct)
    empDF.show(false)

    spark.stop
  }
}
