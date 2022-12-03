package org.adventofcode

package object day2 extends HasFileName {

  override val fileName: String = "day2.txt"

  sealed trait Strategy {
    def score: Int

    def roundWith(other: Strategy): Outcome

    def withOutcome(outcome: Outcome): Strategy
  }

  case object Rock extends Strategy {
    override def score: Int = 1

    override def roundWith(other: Strategy): Outcome =
      other match {
        case Rock => Draw
        case Paper => Loss
        case Scissors => Win
      }

    override def withOutcome(outcome: Outcome): Strategy =
      outcome match {
        case Loss => Scissors
        case Draw => this
        case Win => Paper
      }
  }

  case object Paper extends Strategy {
    override def score: Int = 2

    override def roundWith(other: Strategy): Outcome =
      other match {
        case Rock => Win
        case Paper => Draw
        case Scissors => Loss
      }

    override def withOutcome(outcome: Outcome): Strategy =
      outcome match {
        case Loss => Rock
        case Draw => this
        case Win => Scissors
      }
  }

  case object Scissors extends Strategy {
    override def score: Int = 3

    override def roundWith(other: Strategy): Outcome =
      other match {
        case Rock => Loss
        case Paper => Win
        case Scissors => Draw
      }

    override def withOutcome(outcome: Outcome): Strategy =
      outcome match {
        case Loss => Paper
        case Draw => this
        case Win => Rock
      }
  }

  sealed trait Outcome {
    def score: Int
  }

  case object Loss extends Outcome {
    override def score: Int = 0
  }

  case object Draw extends Outcome {
    override def score: Int = 3
  }

  case object Win extends Outcome {
    override def score: Int = 6
  }
}
