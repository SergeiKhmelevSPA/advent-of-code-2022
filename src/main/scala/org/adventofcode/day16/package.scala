package org.adventofcode

import scala.annotation.tailrec
import scala.collection.View
import scala.language.implicitConversions
import scala.util.matching.Regex

package object day16 extends HasFileName {

  //  override val fileName: String = "day16.txt"
  override implicit val fileName: String = "test.txt"


  def evaluateDay16(initialState: Valve => State): Int = {
    val parsedValves = ResourceLoader.processLines(
      _.map { it => it: Valve }
        .foldLeft(Map.newBuilder[String, Valve])((builder, valve) => builder.addOne(valve.id, valve))
    ).result()

    val valvesConnections: Map[Valve, Set[Valve]] = parsedValves.map { case (_, valve) =>
      (valve, valve.leadsToIds.map(parsedValves(_)))
    }


    val valves: Map[Valve, Map[Valve, Int]] = valvesConnections.keys.map { from =>
      (
        from,
        valvesConnections.keys
          .map { to => (to, findDistance(from, to, valvesConnections)) }
          .filter(_._1.flowRate > 0) // we don't care about 0 flow targets
          .toMap
      )
    }.toMap

    val firstValve = valves.keys.find(_.id == "AA").get

    getMaxTotalFlow(initialState(firstValve), valves)
  }

  implicit def stringToValve(value: String): Valve = {
    val toSingleRegex: Regex = """^Valve (.+) has flow rate=(\d+); tunnel leads to valve (.+)$""".r
    val toMultiRegex: Regex = """^Valve (.+) has flow rate=(\d+); tunnels lead to valves (.+)$""".r

    value match {
      case toSingleRegex(id, rate, leadsTo) => Valve(id, rate.toInt, Set(leadsTo))
      case toMultiRegex(id, rate, leadsTo) => Valve(id, rate.toInt, leadsTo.split(", ").toSet)
    }
  }

  case class Valve(id: String, flowRate: Int, leadsToIds: Set[String]) {

    def flowTotal(minutes: Int): Int = flowRate * minutes

    override def toString: String = id
  }


  def findDistance(start: Valve, target: Valve, valvesConnections: Map[Valve, Set[Valve]]): Int = {

    @tailrec
    def bfs0(elems: List[Valve], visited: List[List[Valve]]): List[List[Valve]] = {
      if (elems.contains(target)) {
        visited
      } else {
        val newNeighbors = elems.flatMap(valvesConnections).filterNot(visited.flatten.contains).distinct
        if (newNeighbors.isEmpty)
          visited
        else
          bfs0(newNeighbors, newNeighbors :: visited)
      }
    }

    bfs0(List(start), List(List(start))).size - 1
  }


  trait State {
    def current: Valve

    def opened: Set[Valve]

    def minutesLeft: Int

    def totalFlow: Int

    def findNextStates(valves: Map[Valve, Map[Valve, Int]]): Set[State]
  }

  private def getMaxTotalFlow(initialState: State, valves: Map[Valve, Map[Valve, Int]]): Int = {

    def goThroughStates(state: State): View[State] = {
      val nextStates = state.findNextStates(valves)
      if (nextStates.isEmpty) {
        View(state)
      } else {
        nextStates.view.flatMap(goThroughStates)
      }
    }

    goThroughStates(initialState)
      .maxBy(_.totalFlow)
      .totalFlow
  }
}
