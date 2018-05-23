package com.ruoze.business

import com.ruoze.Utils.MyPropertiesUtil

private[business] class LogAnalysisArguments(args: Seq[String], env: Map[String, String] = sys.env) {

  if (args.length > 0) {
    MyPropertiesUtil.setPropertiesFile(args(1))
  }
  val properties = MyPropertiesUtil.defaultProperties
  val isCompatibleExceptionData: Boolean = properties.getOrElse("isCompatibleExceptionData", "true").equals("true")
  val fileName =
    if (args.length > 1) args(2)
    else "src/main/resources/emp.txt"
  val master = properties.getOrElse("master", "local")
  val appName = properties.getOrElse("appName", "LogAnalysis")
}
