package org.adventofcode

package object day6 extends HasFileName {

  override val fileName: String = "day6.txt"

  class Checker(len: Int) {
    private val length: Int = len
    private var first: List[Char] = List()
    private var second: List[Char] = List()
    private var index: Int = 0

    def add(segment: Array[Char]): Option[Int] = {
      first = second
      second = segment.toList

      findMarkerIndex()
    }

    private def findMarkerIndex(): Option[Int] = {
      (first ++ second)
        .sliding(length)
        .zipWithIndex
        .foreach { case (elem, i) =>
          if (elem.distinct == elem) {
            return Some(index + i)
          }
        }

      index += length
      None
    }
  }
}
