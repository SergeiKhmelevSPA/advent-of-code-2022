package org.adventofcode.day15

import org.adventofcode.InclusiveRange.collapseRanges
import org.adventofcode.ResourceLoader

object Day15A {

  def main(): Int = {

    val row = 2_000_000

    val allRanges = ResourceLoader.processLines(
      _.map(it => it: SensorWithBeacon)
        .flatMap(_.getNoBeaconRangesInRow(row))
        .toList
    )

    val ranges = collapseRanges(allRanges)

    val min = ranges.map(_.start).min
    val max = ranges.map(_.end).max

    (min to max).count { index => ranges.exists(_.contains(index)) }
  }
}
