package org.adventofcode.day12

import org.adventofcode.ResourceLoader

import scala.collection.View

object Day12A {

  def main(): Int = {

    val builder = new HeightmapBuilder

    ResourceLoader.processLineByLine(builder.processLine)

    val heightmap = builder.build()

    val start = heightmap.start

    //    val a = heightmap(1)(0)
    //    val b = heightmap(1)(1)
    //    println(a)
    //    println(b)
    //    println(a isClimbableFrom a)


    //    println(heightmap.findNextSteps(start))
    //    val allRouts = new HeightmapWalker(heightmap).findRouts().sortBy(_.size)
    //    println(allRouts.mkString("\n"))
    val minPath = new HeightmapWalker(heightmap).findRouts().minBy(_.size)
    //    println(minPath)
    //    println(minPath.size)


    minPath.size - 1
  }

  class HeightmapWalker(heightmap: Heightmap) {

    def findRouts(): Seq[Set[Square]] = {
      findRouts(heightmap.start, Set[Square]()).flatten
    }

    def findRouts(origin: Square, path: Set[Square]): Seq[Option[Set[Square]]] = {
      val path2 = path + origin
      heightmap.findNextSteps(origin).flatMap { next: Square =>
        if (next.isFinish) {
          View(Some(path2 + next))
        }
        else if (!path2.contains(next)) {
          findRouts(next, path2)
        } else {
          View(None)
        }
      }
    }
  }
}
