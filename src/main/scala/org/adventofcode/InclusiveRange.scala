package org.adventofcode

case class InclusiveRange(start: Int, end: Int) extends Ordered[InclusiveRange] {
  assert(start <= end)

  /** Returns true if given Range is completely contained in this range */
  def contains(rhs: InclusiveRange): Boolean = start <= rhs.start && rhs.end <= end

  /** Returns true if given value is contained in this range */
  def contains(v: Int): Boolean = start <= v && v <= end

  override def compare(that: InclusiveRange): Int = {
    start compare that.start match {
      case 0 => end compare that.end
      case other => other
    }
  }

  override def toString: String = s"[$start, $end]"
}


object InclusiveRange {

  def collapseRanges(ranges: List[InclusiveRange]): List[InclusiveRange] =
    ranges.sorted
      .foldLeft(List.empty[InclusiveRange])(foldOp)
      .reverse

  val foldOp: (List[InclusiveRange], InclusiveRange) => List[InclusiveRange] = {
    (list, range) =>
      list match {
        case head :: tail if head contains range => head :: tail
        case head :: tail if head contains range.start =>
          InclusiveRange(head.start, math.max(head.end, range.end)) :: tail
        case _ => range :: list
      }
  }
}
