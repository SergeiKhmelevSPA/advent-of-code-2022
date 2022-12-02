package org.adventofcode

import scala.language.reflectiveCalls

object GetResource {
  def using[A <: {def close(): Unit}, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}
