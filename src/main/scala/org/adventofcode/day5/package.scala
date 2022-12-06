package org.adventofcode

import scala.language.implicitConversions
import scala.util.matching.Regex

package object day5 extends HasFileName {
  override val fileName: String = "day5.txt"


  implicit def stringToCommand(value: String): Command = {
    val pattern: Regex = """^move (\d+) from (\d+) to (\d+)$""".r

    value match {
      case pattern(amount, from, to) => Command(amount.toInt, from.toInt, to.toInt)
    }
  }

  /** move 5 from 8 to 2 */
  case class Command(amount: Int, from: Int, to: Int)
}
