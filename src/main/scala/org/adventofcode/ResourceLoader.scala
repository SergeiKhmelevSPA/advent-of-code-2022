package org.adventofcode

import scala.io.Source
import scala.language.reflectiveCalls

object ResourceLoader {

  def reduceLineByLine[R](fileName: String, f: String => R, op: (R, R) => R): R =
    processLineByLine(fileName, _.map(f).reduce(op))

  def reduceLineByLine(fileName: String, f: String => Int, op: (Int, Int) => Int = _ + _): Int =
    processLineByLine(fileName, lines => lines.map(f).reduce(op))

  def processLineByLine[R](fileName: String, f: Iterator[String] => R): R =
    using(Source.fromResource(fileName)) { source =>
      f(source.getLines())
    }

  private def using[A <: {def close(): Unit}, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}
