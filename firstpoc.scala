package org.example

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
case class Book(book_name: String, cost: Int, writer_id: Int)

case class Writer(writer_name: String, writer_id: Int)

object firstpoc {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("example").getOrCreate()

    try {
     // val data = spark.read.textFile("C:\\Users\\user\\IdeaProjects\\firstscalaspark\\src\\main\\resources\\data.txt")

      //data.show(false)

      val data = Seq(("James", "A", "Smith", "2018", "M", 3000),
        ("Michael", "Rose", "Jones", "2010", "M", 4000),
        ("Robert", "K", "Williams", "2010", "M", 6000),
        ("Maria", "Anne", "Jones", "2005", "F", 4500),
        ("Jen", "Mary", "Brown", "2010", "", 2500)
      )

      val columns = Seq("fname", "mname", "lname", "dob_year", "gender", "salary")
      val rdd =spark.sparkContext.parallelize(data)

      val df = spark.createDataFrame(rdd).toDF(columns: _*)
      df.show(false)

      val df2 = df.where("salary > 3000")
      df2.show(false)

      val df3 = df2.withColumn("department",lit("IT"))
      df3.show(false)
      //data.write.csv("F://data-output")

      import spark.implicits._

      val bookDS = Seq(
        Book("Scala", 400, 1),
        Book("Spark", 500, 2),
        Book("Kafka", 300, 3),
        Book("Java", 350, 5)).toDF()

      bookDS.show()

      val writerDS = Seq(
        Writer("Martin", 1),
        Writer("Zaharia ",2),
        Writer("Neha", 3),
        Writer("James", 4)).toDF()

      writerDS.show()

      val BookWriterInner = bookDS.join(writerDS, bookDS("writer_id") === writerDS("writer_id"), "inner")
      BookWriterInner.show()

    } finally {
      spark.close()
    }
  }

}
