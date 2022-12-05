package org.adventofcode

import scala.io.Source
import scala.language.reflectiveCalls

object ResourceLoader {

  def reduceLineByLine[R](f: String => R, op: (R, R) => R)(implicit fileName: String): R =
    processLines(_.map(f).reduce(op))

  def reduceLineByLine(f: String => Int, op: (Int, Int) => Int = _ + _)(implicit fileName: String): Int =
    processLines(_.map(f).reduce(op))

  def processLines[R](f: Iterator[String] => R)(implicit fileName: String): R =
    using(Source.fromResource(fileName)) { source =>
      f(source.getLines())
    }

  def processLineByLine(f: String => Unit)(implicit fileName: String) =
    processLines(_.foreach(f))

  private def using[A <: {def close(): Unit}, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}

trait HasFileName {
  implicit val fileName: String
}