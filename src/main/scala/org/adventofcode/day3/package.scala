package org.adventofcode

package object day3 extends HasFileName {

  override implicit val fileName: String = "day3.txt"

  private val map = (('a' to 'z').lazyZip(1 to 26) ++ ('A' to 'Z').lazyZip(27 to 52)).toMap

  implicit class ItemPriority(element: Char) {

    /**
     * Lowercase item types a through z have priorities 1 through 26.
     *
     * Uppercase item types A through Z have priorities 27 through 52.
     */
    def priority(): Int = map(element)
  }
}
