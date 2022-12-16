package org.adventofcode

import scala.language.implicitConversions
import scala.util.matching.Regex

package object day10 extends HasFileName {

  override val fileName: String = "day10.txt"

  implicit def stringToCommand(value: String): Command = {
    val noop: Regex = """^noop$""".r
    val addX: Regex = """^addx (-?\d+)$""".r

    value match {
      case noop() => Noop
      case addX(d) => AddX(d.toInt)
    }
  }

  sealed trait Command

  case object Noop extends Command

  case class AddX(value: Int) extends Command
}
