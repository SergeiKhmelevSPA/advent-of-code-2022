package org.adventofcode.day1

import org.adventofcode.ResourceLoader

import scala.collection.mutable

object Day1B {

  def main(args: Array[String]): Unit = {
    val context = new Context()

    ResourceLoader.processLineByLine(
      _.foreach(calc(context, _))
    )

    context.calculate()

    val result = context.sum()

    println(result)
    assert(result == 215594)
  }

  private def calc(context: Context, line: String): Unit =
    if (line.isBlank) {
      context.calculate()
    } else {
      context.add(line.toInt)
    }

  private class Context(
                         private var maxList: Array[Int] = new mutable.ArrayBuilder.ofInt().addAll(Array(0, 0, 0)).result(), // ja pierdolę
                         private var current: Int = 0,
                       ) {

    def add(value: Int): Unit = {
      current += value
    }

    def calculate(): Unit = {
      var running = current
      for (i <- maxList.indices) {
        val el = maxList(i)

        // could be implemented better
        maxList(i) = math.max(el, running)
        running = math.min(el, running)
      }

      current = 0
    }

    def sum(): Int = maxList.sum
  }
}
