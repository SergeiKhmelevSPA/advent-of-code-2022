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

    val lcmValue: Long = lcm(monkeys.values.map(_.divisibleBy))

    monkeys.values.foreach(_.setLcm(lcmValue))

    1 to 10_000 foreach { _ =>
      for ((_, monkey) <- monkeys) {
        val actions = monkey.inspect()
        actions.foreach { action => monkeys(action.toMonkey).receiveAnItem(action.item) }
      }
    }

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
                 divisibleBy: Long,
                 onTrue: Int,
                 onFalse: Int,
               ) extends Monkey(id, startingItems, operation, divisibleBy, onTrue, onFalse) {

    private var lcm: Long = 0

    // TODO oh, come on
    def setLcm(value: Long): Unit = {
      lcm = value
    }

    override protected def inspectItem(item: Item): ThrowAction = {
      val newWorryLevel = inspectOperation(item) % lcm
      val toMonkey = if (testWorryLevel(newWorryLevel)) throwToMonkeyXOnTrue else throwToMonkeyXOnFalse

      inspectionsCount += 1
      //println(s"$id inspects $item: $newWorryLevel -> $toMonkey")

      ThrowAction(newWorryLevel, toMonkey)
    }
  }

  /** https://stackoverflow.com/questions/40875537/fp-lcm-in-scala-in-1-line */
  def lcm(list: Iterable[Long]): Long = list.foldLeft(1: Long) {
    (a, b) =>
      b * a /
        LazyList.iterate((a, b)) { case (x, y) => (y, x % y) }.dropWhile(_._2 != 0).head._1.abs
  }
}
