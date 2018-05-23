package com.ruoze.Utils

import java.text.SimpleDateFormat
import java.util.Date

import com.ruoze.Utils.`trait`.MyLoggin

object DateUtil extends MyLoggin {
  val inputDate = new SimpleDateFormat("yyyy/MM/dd")
  val outputDate = new SimpleDateFormat("yyyyMMdd")

  def parseTime(time: String): Date = {
    inputDate.parse(time)
  }

  def getTime(time: String): String = {
    outputDate.format(parseTime(time))
  }
}
