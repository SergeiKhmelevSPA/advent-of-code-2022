package org.adventofcode.day11

import org.adventofcode.ResourceLoader

import scala.collection.immutable.ListMap

object Day11B {

  def main(): Long = {
    val monkeysBuilder = ListMap.newBuilder[Int, MonkeyB]

    ResourceLoader.processLines(
      _.grouped(7)
        .foreach { lines =>
          monkeysBuilder.addOne(parseMonkey(lines, new MonkeyB(_, _, _, _, _, _)))
        }
    )

    val monkeys = monkeysBuilder.result()

    1 to 20 foreach { _ =>
      for ((i, monkey) <- monkeys) {
        val actions = monkey.inspect()
        actions.foreach { action => monkeys(action.toMonkey).receiveAnItem(action.item) }
      }
    }

    println(monkeys.values
      .map(_.getInspectionsCount)
      .toSeq
      //      .sorted(Ordering[Int].reverse)
      //      .take(2)
    )
    monkeys.values
      .map(_.getInspectionsCount)
      .toSeq
      .sorted(Ordering[Int].reverse)
      .take(2)
      .map(_.toLong)
      .product
  }

  class MonkeyB(
                 id: Int,
                 startingItems: List[Item],
                 operation: Item => Item,
                 divisibleBy: Int,
                 onTrue: Int,
                 onFalse: Int,
               ) extends Monkey(id, startingItems, operation, divisibleBy, onTrue, onFalse) {

    override protected def inspectItem(item: Item): ThrowAction = {
      val newWorryLevel = inspectOperation(item)
      val toMonkey = if (testWorryLevel(newWorryLevel)) throwToMonkeyXOnTrue else throwToMonkeyXOnFalse

      inspectionsCount += 1
      //println(s"$id inspects $item: $newWorryLevel -> $toMonkey")

      ThrowAction(newWorryLevel, toMonkey)
    }
  }
}
