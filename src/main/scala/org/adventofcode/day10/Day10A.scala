package org.adventofcode.day10

import org.adventofcode.{ResourceLoader, day10}

object Day10A {

  def main(): Int = {
    val state = ResourceLoader.processLines(
      _.foldLeft(new State)(_.applyCommand(_))
    )

    state.signalStrengthsSum()
  }

  class State {
    private val cyclesOfInterest: Set[Int] = (20 to 220 by 40).toSet

    private var cycle: Int = 0
    private var signalValue: Int = 1

    private var result: Int = 0

    def applyCommand(command: Command): State = {
      nextCycle()

      command match {
        case day10.Noop => /* do nothing */
        case AddX(value) => {
          nextCycle()
          signalValue += value
        }
      }

      this
    }

    private def nextCycle(): Unit = {
      cycle += 1

      if (cyclesOfInterest.contains(cycle)) {
        result += signalValue * cycle
      }
    }

    def signalStrengthsSum(): Int = {
      result
    }
  }
}
