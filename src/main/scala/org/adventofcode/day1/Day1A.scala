package org.adventofcode.day1

import org.adventofcode.GetResource.using

import scala.io.Source

object Day1A {

  def main(args: Array[String]): Unit = {
    val context = new Context()

    using(Source.fromResource("day1.txt")) { source => {
      for (line <- source.getLines) {
        calc(context, line)
      }
      context.calculate()
    }
    }

    println(context)
  }

  private def calc(context: Context, line: String): Unit =
    if (line.isBlank) {
      context.calculate()
    } else {
      context.add(line.toInt)
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

    override def toString = s"Context($max)"
  }
}
