package com.ruoze.Utils

import com.ruoze.Utils.`trait`.MyLoggin

object MathsUtil extends MyLoggin {
  def isInteger(value: String): Boolean = try {
    value.toInt
    true
  } catch {
    case e: NumberFormatException =>
      false
  }

  /**
    * 判断字符串是否是浮点数
    */
  def isDouble(value: String): Boolean = try {
    value.toDouble
    if (value.contains(".")) return true
    false
  } catch {
    case e: NumberFormatException =>
      false
  }

  /**
    * 判断字符串是否是数字
    */
  def isNumber(value: String): Boolean = isInteger(value) || isDouble(value)
}
