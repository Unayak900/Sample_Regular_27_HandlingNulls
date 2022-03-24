package com.spark2.dataframes

import org.apache.spark.sql.SparkSession

object Save_to_hive {

  case class Purchase(customer_id:Int,purchase_id:Int,day:String,time:String,tz:String,amount:Double)

  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder
      .config("spark.master", "local")
      .appName("Save_to_hive")
      .config("spark.sql.warehouse.dir", "http://localhost:9870/user/hive/warehouse")
      .enableHiveSupport()
      .getOrCreate()

   // val spark =SparkSession.builder.appName("Spark Hive Example")
     // .config("spark.sql.warehouse.dir", ".").enableHiveSupport().getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    import spark.implicits._
    import spark.sql

    val purchaseDF = List(Purchase(121,234,"2017-04-19","20:50","UTC",500.29)).toDF()

    purchaseDF.show(false)
    println("*********Save to hive******************")
    purchaseDF.coalesce(1).write.mode("append").insertInto("tech.sales")




  }
}