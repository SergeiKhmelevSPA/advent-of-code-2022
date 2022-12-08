package org.adventofcode.day2

import org.adventofcode.ResourceLoader

import scala.language.implicitConversions

object Day2A {

  def main(): Int = {
    ResourceLoader.mapAndSum { line =>
      val items = line.split(' ')
      val opponentStrategy: Strategy = items(0)
      val yourStrategy: Strategy = items(1)

      yourStrategy.score + yourStrategy.roundWith(opponentStrategy).score
    }
  }

  private implicit def stringToStrategy(value: String): Strategy =
    value match {
      case "A" | "X" => Rock
      case "B" | "Y" => Paper
      case "C" | "Z" => Scissors
    }
}
