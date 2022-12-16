package org.adventofcode.day10

import org.adventofcode.{ResourceLoader, day10}

object Day10B {

  def main(): String = {
    val state = ResourceLoader.processLines(
      _.foldLeft(new State)(_.applyCommand(_))
    )

    state.output()
  }

  class State {
    private var cycle: Int = 0
    private var spritePosition: Int = 1

    private val builder = new StringBuilder

    def applyCommand(command: Command): State = {
      nextCycle()

      command match {
        case day10.Noop => /* do nothing */
        case AddX(value) => {
          nextCycle()
          spritePosition += value
        }
      }

      this
    }

    private def nextCycle(): Unit = {
      if (isSpriteVisible) {
        builder.append("#")
        //print('#')
      } else {
        builder.append(".")
        //print('.')
      }

      cycle += 1

      if (cycle == 40) {
        cycle = 0
        builder.append("\n")
        //println()
      }
    }

    private def isSpriteVisible: Boolean = {
      cycle == spritePosition || cycle == spritePosition + 1 || cycle == spritePosition - 1
    }

    def output(): String = builder.toString()
  }
}
