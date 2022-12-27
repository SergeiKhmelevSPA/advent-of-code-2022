package org.adventofcode.day16

object Day16A {

  def main(): Int = {
    evaluateDay16(start => StateA(start, Set.empty, 30, 0))
  }

  case class StateA(
                     current: Valve,
                     opened: Set[Valve],
                     minutesLeft: Int,
                     totalFlow: Int,
                   ) extends State {

    def findNextStates(valves: Map[Valve, Map[Valve, Int]]): Set[State] = {
      valves(current)
        .filterNot(opened contains _._1)
        .flatMap { case (valve, dist) => moveToAndOpen(valve, dist) }
        .toSet
    }

    private def moveToAndOpen(nextValve: Valve, dist: Int): Option[State] = {
      val nextMinuteLeft = minutesLeft - dist - 1
      if (nextMinuteLeft >= 0) {
        Some(StateA(nextValve, opened + nextValve, nextMinuteLeft, totalFlow + nextValve.flowTotal(nextMinuteLeft)))
      } else {
        None
      }
    }
  }
}
