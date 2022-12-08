package org.adventofcode

import scala.io.Source
import scala.language.reflectiveCalls

object ResourceLoader {

  def reduceLineByLine[R](f: String => R, op: (R, R) => R)(implicit fileName: String): R =
    processLines(_.map(f).reduce(op))

  def mapAndSum(f: String => Int)(implicit fileName: String): Int =
    processLines(_.map(f).sum)

  def processLines[R](f: Iterator[String] => R)(implicit fileName: String): R =
    using(Source.fromResource(fileName)) { source =>
      f(source.getLines())
    }

  def processLineByLine(f: String => Unit)(implicit fileName: String): Unit =
    processLines(_.foreach(f))

  def using[A <: {def close(): Unit}, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}

trait HasFileName {
  implicit val fileName: String
}