package org.adventofcode.day2

import org.adventofcode.GetResource.using

import scala.io.Source

object Day2B {

  def main(args: Array[String]): Unit = {
    var result = 0

    using(Source.fromResource("day2.txt")) { source => {
      for (line <- source.getLines) {
        result += calc(line)
      }
    }
    }

    println(result)
  }

  private def calc(line: String): Int = {
    val items = line.split(' ')
    val opponentStrategy = Strategy.parse(items(0))
    val expectedOutcome = Outcome.parse(items(1))

    expectedOutcome.score + opponentStrategy.withOutcome(expectedOutcome).score
  }

  private object Strategy {

    def parse(value: String): Strategy =
      value match {
        case "A" => Rock
        case "B" => Paper
        case "C" => Scissors
      }
  }

  private sealed trait Strategy {
    def score: Int

    def withOutcome(outcome: Outcome): Strategy
  }

  private case object Rock extends Strategy {
    override def score: Int = 1

    override def withOutcome(outcome: Outcome): Strategy =
      outcome match {
        case Loss => Scissors
        case Draw => this
        case Win => Paper
      }
  }

  private case object Paper extends Strategy {
    override def score: Int = 2

    override def withOutcome(outcome: Outcome): Strategy =
      outcome match {
        case Loss => Rock
        case Draw => this
        case Win => Scissors
      }
  }

  private case object Scissors extends Strategy {
    override def score: Int = 3

    override def withOutcome(outcome: Outcome): Strategy =
      outcome match {
        case Loss => Paper
        case Draw => this
        case Win => Rock
      }
  }


  private object Outcome {

    def parse(value: String): Outcome =
      value match {
        case "X" => Loss
        case "Y" => Draw
        case "Z" => Win
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
