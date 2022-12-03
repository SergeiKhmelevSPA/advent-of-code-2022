package org.adventofcode.day1

import org.adventofcode.ResourceLoader

object Day1A {

  def main(): Int = {
    val context = new Context()

    ResourceLoader.processLineByLine(_.foreach { line =>
      if (line.isBlank) {
        context.calculate()
      } else {
        context.add(line.toInt)
      }
    })

    context.calculate()

    context.result()
  }

  private class Context(
                         private var max: Int = -1,
                         private var current: Int = 0,
                       ) {

    def add(value: Int): Unit = {
      current += value
    }

    def calculate(): Unit = {
      max = math.max(max, current)
      current = 0
    }

    def result(): Int = max
  }
}
