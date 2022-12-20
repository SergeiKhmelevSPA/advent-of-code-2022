package org.adventofcode

import scala.annotation.tailrec

package object day12 extends HasFileName {

  override val fileName: String = "day12.txt"

  class Node(val x: Int, val y: Int, parsedElevation: Char) {

    val isStart: Boolean = parsedElevation == 'S'
    val isEnd: Boolean = parsedElevation == 'E'

    val elevation: Char = if (isStart) 'a' else if (isEnd) 'z' else parsedElevation


    /** at most one higher */
    def isClimbableFrom(origin: Node): Boolean = {
      elevation - origin.elevation <= 1
    }

    override def toString: String = s"($x, $y : $elevation)"


    def canEqual(other: Any): Boolean = other.isInstanceOf[Node]

    override def equals(other: Any): Boolean = other match {
      case that: Node =>
        (that canEqual this) &&
          x == that.x &&
          y == that.y
      case _ => false
    }

    override def hashCode(): Int = {
      val state = Seq(x, y)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
  }

  class HeightmapBuilder(
                          climbableCondition: (Node, Node) => Boolean
                        ) {
    private val matrixBuilder = Array.newBuilder[Array[Node]]

    private var yIndex = 0

    def processLine(line: String): Unit = {
      matrixBuilder += line.zipWithIndex
        .map { case (el: Char, xIndex: Int) => new Node(xIndex, yIndex, el) }
        .toArray
      yIndex += 1
    }

    private def matrixToGraph(matrix: Array[Array[Node]]): Map[Node, Set[Node]] = {
      val sizeX: Int = matrix(0).length
      val sizeY: Int = matrix.length

      def findNextSteps(origin: Node): Set[Node] = {
        val builder = Set.newBuilder[Node]

        def add(target: Node): Unit = {
          if (climbableCondition(origin, target)) {
            builder.addOne(target)
          }
        }

        if (origin.x - 1 >= 0) {
          add(matrix(origin.y)(origin.x - 1))
        }
        if (origin.x + 1 < sizeX) {
          add(matrix(origin.y)(origin.x + 1))
        }
        if (origin.y - 1 >= 0) {
          add(matrix(origin.y - 1)(origin.x))
        }
        if (origin.y + 1 < sizeY) {
          add(matrix(origin.y + 1)(origin.x))
        }


        builder.result()
      }

      val builder = Map.newBuilder[Node, Set[Node]]

      for (row <- matrix) {
        for (el <- row) {
          builder.addOne(el, findNextSteps(el))
        }
      }

      builder.result()
    }

    def build(): Heightmap = new Heightmap(matrixToGraph(matrixBuilder.result))
  }

  class Heightmap(val graph: Map[Node, Set[Node]]) {

    val start: Node = graph.keys.find(_.isStart).get
    val end: Node = graph.keys.find(_.isEnd).get

    def adjacent(from: Node): Set[Node] = graph(from)
  }

  class HeightmapWalker(
                         heightmap: Heightmap,
                         startNode: Node,
                         endCondition: Node => Boolean
                       ) {

    def shortestPathSize(): Int = bfs(startNode).size - 1

    private def bfs(start: Node): Seq[List[Node]] = {

      @tailrec
      def bfs0(elems: List[Node], visited: List[List[Node]]): List[List[Node]] = {
        if (elems.exists(endCondition)) {
          visited
        } else {
          val newNeighbors = elems.flatMap(heightmap.adjacent).filterNot(visited.flatten.contains).distinct
          if (newNeighbors.isEmpty)
            visited
          else
            bfs0(newNeighbors, newNeighbors :: visited)
        }
      }

      bfs0(List(start), List(List(start))).reverse
    }
  }
}
