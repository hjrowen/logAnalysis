package com.ruoze.Utils

import com.ruoze.Utils.`trait`.MyLoggin
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

object EmpParse extends MyLoggin {
  //empno ename job mgr hiredate sla comm deptno
  val struct = StructType(Array(
    StructField("empno", StringType, nullable = false),
    StructField("ename", StringType, nullable = true),
    StructField("job", StringType, nullable = true),
    StructField("mgr", StringType, nullable = true),
    StructField("hiredate", StringType, nullable = true),
    StructField("sla", DoubleType, nullable = true),
    StructField("comm", DoubleType, nullable = true),
    StructField("deptno", StringType, nullable = true)))

  private val STRUCT_SIZE = struct.length
  val ERROR_ROW = Row(0)
  def errorRow: Row = ERROR_ROW

  /***
    * 对日志记录进行格式化
    * @param arr 被分隔后的日志记录
    * @param isCompatibleExceptionData 是否对异常数据作兼容
    */
  def formated(arr: Array[String], isCompatibleExceptionData: Boolean): Unit = {
    if (isCompatibleExceptionData) {
      // 这2个字段后续将转为double类型，如果源字段不是数字，则抛异常
      if (!MathsUtil.isNumber(arr(5).trim)) arr(5) = "0"
      if (!MathsUtil.isNumber(arr(6).trim)) arr(6) = "0"
    }
    arr(4) = DateUtil.getTime(arr(4))
  }

  def paresContent(content: String, isCompatibleExceptionData: Boolean): Row = {
    val arr = content.split("\t")
    try {
      formated(arr,isCompatibleExceptionData)
      if (arr.length != STRUCT_SIZE)
        ERROR_ROW
      else
        Row(arr(0), arr(1), arr(2), arr(3), arr(4), arr(5).trim.toDouble, arr(6).trim.toDouble, arr(7))
    } catch {
      case ex : Exception => {
        ERROR_ROW
      }
    }
  }

}
