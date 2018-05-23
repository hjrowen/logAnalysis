package com.ruoze.Utils

import java.io.{File, FileInputStream, IOException, InputStreamReader}
import java.nio.charset.StandardCharsets
import java.util.Properties

import com.ruoze.Utils.`trait`.MyLoggin

import scala.collection.mutable.HashMap

object MyPropertiesUtil extends MyLoggin{
  import scala.collection.JavaConverters._

  // 默认配置文件
  var propertiesFile = "src/main/resources/myConfig"

  def setPropertiesFile(file: String) : Unit = {
    propertiesFile = file
  }

  def getPropertiesFromFile(filename: String): Map[String, String] = {
    val file = new File(filename)
    require(file.exists(), s"Properties file $file does not exist")
    require(file.isFile(), s"Properties file $file is not a normal file")

    val inReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)
    try {
      val properties = new Properties()
      properties.load(inReader)
      properties.stringPropertyNames().asScala.map(
        k => (k, properties.getProperty(k).trim)).toMap
    } catch {
      case e: IOException =>
        throw new IOException(s"Failed when loading properties from $filename", e)
    } finally {
      inReader.close()
    }
  }
  lazy val defaultProperties: HashMap[String, String] = {
    val defaultProperties = new HashMap[String, String]()
    Option(propertiesFile).foreach { filename =>
      val properties = MyPropertiesUtil.getPropertiesFromFile(filename)
      properties.foreach { case (k, v) =>
        defaultProperties(k) = v
      }
    }
    defaultProperties
  }
}
