package Var1

import scala.util.matching.Regex

object Main17 {
  def main(args: Array[String]): Unit = {
    val input = "Hello to everybody"
    println(input)

    val pattern: Regex = "y|Y".r

    val result = pattern.replaceAllIn(input, "")

    println("Without y's: " + result)
  }
}