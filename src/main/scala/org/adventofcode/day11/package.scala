package org.adventofcode

import scala.util.matching.Regex

package object day11 extends HasFileName {

  override val fileName: String = "test.txt"


  def parseMonkey[T <: Monkey](
                                lines: Seq[String],
                                constructor: (Int, List[Item], Item => Item, Item, Item, Item) => T
                              ): (Int, T) = {
    val idRegex: Regex = """^Monkey (\d+):$""".r
    val itemsRegex: Regex = """^\W+Starting items: ([, \d]+)$""".r
    val operationMultiRegex: Regex = """^\W+Operation: new = old \* (\d+)$""".r
    val operationMultiSelfRegex: Regex = """^\W+Operation: new = old \* old$""".r
    val operationSumRegex: Regex = """^\W+Operation: new = old \+ (\d+)$""".r
    val testRegex: Regex = """^\W+Test: divisible by (\d+)$""".r
    val onTrueRegex: Regex = """^\W+If true: throw to monkey (\d+)$""".r
    val onFalseRegex: Regex = """^\W+If false: throw to monkey (\d+)$""".r
    val emptyRegex: Regex = """^\W*$""".r

    var id: Int = -1
    var items: List[Item] = List.empty
    var operation: Item => Item = { it => it }
    var divisibleBy: Item = -1
    var onTrue: Item = -1
    var onFalse: Item = -1

    lines.foreach {
      case idRegex(idString) => id = idString.toInt
      case itemsRegex(itemsString) => {
        items = itemsString.split(",")
          .map(_.trim)
          .map(_.toInt)
          .toList
      }
      case operationMultiRegex(value) => operation = _ * value.toInt
      case operationMultiSelfRegex() => operation = { it => it * it }
      case operationSumRegex(value) => operation = _ + value.toInt
      case testRegex(value) => divisibleBy = value.toInt
      case onTrueRegex(value) => onTrue = value.toInt
      case onFalseRegex(value) => onFalse = value.toInt
      case emptyRegex() =>
      case default => println(s"WTF: '$default")
    }

    val monkey = constructor(id, items, operation, divisibleBy, onTrue, onFalse)

    (id, monkey)
  }

  abstract class Monkey(
                         val id: Int,
                         startingItems: List[Item],
                         operation: Item => Item,
                         divisibleBy: Int,
                         onTrue: Int,
                         onFalse: Int,
                       ) {

    protected var items: List[Item] = startingItems
    protected val inspectOperation: Item => Item = operation
    protected val testWorryLevel: Item => Boolean = _ % divisibleBy == 0
    protected val throwToMonkeyXOnTrue: Int = onTrue
    protected val throwToMonkeyXOnFalse: Int = onFalse

    protected var inspectionsCount: Int = 0

    def inspect(): List[ThrowAction] = {
      val actions = items.map(inspectItem)
      items = List.empty[Item]
      actions
    }

    protected def inspectItem(item: Item): ThrowAction

    def receiveAnItem(item: Item): Unit = {
      items = items :+ item
    }

    def getInspectionsCount: Int = inspectionsCount
  }

  case class ThrowAction(item: Item, toMonkey: Int)

  type Item = Int
}
