package org.adventofcode.day2

import org.adventofcode.ResourceLoader

import scala.language.implicitConversions

object Day2B {

  def main(): Int = {
    ResourceLoader.reduceLineByLine { line =>
      val items = line.split(' ')
      val opponentStrategy: Strategy = items(0)
      val expectedOutcome: Outcome = items(1)

      expectedOutcome.score + opponentStrategy.withOutcome(expectedOutcome).score
    }
  }

  private implicit def stringToStrategy(value: String): Strategy =
    value match {
      case "A" => Rock
      case "B" => Paper
      case "C" => Scissors
    }

  private implicit def stringToOutcome(value: String): Outcome =
    value match {
      case "X" => Loss
      case "Y" => Draw
      case "Z" => Win
    }
}
