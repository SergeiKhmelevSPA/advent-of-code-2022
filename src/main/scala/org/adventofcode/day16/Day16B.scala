package org.adventofcode.day16

object Day16B {

  def main(): Int = {
    evaluateDay16(start => StateB(start, Set.empty, 26, 0))
  }

  case class StateB(
                     current: Valve,
                     opened: Set[Valve],
                     minutesLeft: Int,
                     totalFlow: Int,
                   ) extends State {

    def findNextStates(valves: Map[Valve, Map[Valve, Int]]): Set[State] = {
      valves(current)
        .filterNot(opened contains _._1)
        .toList
        .combinations(2)
        .flatMap {
          case head :: next :: Nil =>
            List(moveToAndOpen2(head._1, head._2), moveToAndOpen2(next._1, next._2))
          case el :: Nil => moveToAndOpen2(el._1, el._2)
          case Nil => ???
        }
        .flatten
        .toSet
    }

    private def moveToAndOpen2(nextValve1: Valve, dist1: Int, nextValve2: Valve, dist2: Int): List[State] = {
      val nextMinuteLeft = minutesLeft - dist1 - dist2 - 2
      if ()
    }

    private def moveToAndOpen(nextValve: Valve, dist: Int): Option[State] = {
      val nextMinuteLeft = minutesLeft - dist - 1
      if (nextMinuteLeft >= 0) {
        Some(StateB(nextValve, opened + nextValve, nextMinuteLeft, totalFlow + nextValve.flowTotal(nextMinuteLeft)))
      } else {
        None
      }
    }
  }
}
