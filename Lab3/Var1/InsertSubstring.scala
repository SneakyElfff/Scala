package Var1

import scala.util.matching.Regex

object Main18 {
  def main(args: Array[String]): Unit = {
    val input = "Hello to everybody"

    val pattern: Regex = "Hello".r

    val result = pattern.replaceFirstIn(input, "$0 with heartiness")

    println(result)
  }
}