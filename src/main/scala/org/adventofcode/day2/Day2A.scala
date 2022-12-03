package org.adventofcode.day2

import org.adventofcode.ResourceLoader

object Day2A {

  def main(): Int = {
    ResourceLoader.reduceLineByLine { line =>
      val items = line.split(' ')
      val opponentStrategy = Strategy.parse(items(0))
      val yourStrategy = Strategy.parse(items(1))

      yourStrategy.score + yourStrategy.roundWith(opponentStrategy).score
    }
  }

  private object Strategy {

    def parse(value: String): Strategy =
      value match {
        case "A" | "X" => Rock
        case "B" | "Y" => Paper
        case "C" | "Z" => Scissors
      }
  }

  private sealed trait Strategy {
    def score: Int

    def roundWith(other: Strategy): Outcome
  }

  private case object Rock extends Strategy {
    override def score: Int = 1

    override def roundWith(other: Strategy): Outcome =
      other match {
        case Rock => Draw
        case Paper => Loss
        case Scissors => Win
      }
  }

  private case object Paper extends Strategy {
    override def score: Int = 2

    override def roundWith(other: Strategy): Outcome =
      other match {
        case Rock => Win
        case Paper => Draw
        case Scissors => Loss
      }
  }

  private case object Scissors extends Strategy {
    override def score: Int = 3

    override def roundWith(other: Strategy): Outcome =
      other match {
        case Rock => Loss
        case Paper => Win
        case Scissors => Draw
      }
  }


  private sealed trait Outcome {
    def score: Int
  }

  private case object Loss extends Outcome {
    override def score: Int = 0
  }

  private case object Draw extends Outcome {
    override def score: Int = 3
  }

  private case object Win extends Outcome {
    override def score: Int = 6
  }

}
