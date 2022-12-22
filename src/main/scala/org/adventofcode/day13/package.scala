package org.adventofcode

import scala.collection.mutable
import scala.util.control.Breaks.{break, breakable}
import scala.util.matching.Regex

package object day13 extends HasFileName {

  override val fileName: String = "day13.txt"


  def convertToPacket(input: String): Packet = {
    val pattern: Regex = """^\[(.*)]$""".r

    val s = input match {
      case pattern(content) => content
    }

    var i = 0

    def fillList(packets: mutable.Builder[Packet, List[Packet]]): Packet = {
      breakable {
        while (i < s.length) {
          val ch = s(i)
          if (ch == '[') {
            i += 1
            packets.addOne(fillList(List.newBuilder[Packet]))
          } else if (ch == ']') {
            break
          } else if (ch == ',') {
            // do nothing
          } else {
            val newI = Array(s.indexOf(',', i), s.indexOf(']', i))
              .filterNot(_ == -1)
              .minOption
              .getOrElse(s.length)

            packets.addOne(Signal(s.substring(i, newI).toInt))
            i = newI - 1
          }

          i += 1
        }
      }

      PacketList(packets.result())
    }

    fillList(List.newBuilder[Packet])
  }


  sealed trait Packet extends Ordered[Packet]

  case class Signal(value: Int) extends Packet {

    override def compare(that: Packet): Int = {
      that match {
        case Signal(thatValue) => value compare thatValue
        case PacketList(Nil) => 1
        case PacketList(thatHead :: thatTail) => {
          this compare thatHead match {
            case 0 => if (thatTail.isEmpty) 0 else -1
            case default => default
          }
        }
      }
    }

    override def toString: String = value.toString
  }

  case class PacketList(packets: List[Packet]) extends Packet {

    override def compare(that: Packet): Int = {
      that match {
        case Signal(_) => Math.negateExact(that compare this)
        case PacketList(Nil) => if (packets.isEmpty) 0 else 1
        case PacketList(thatHead :: thatNext) => {
          packets match {
            case Nil => -1
            case head :: next => {
              head compare thatHead match {
                case 0 => PacketList(next) compare PacketList(thatNext)
                case default => default
              }
            }
          }
        }
      }
    }

    override def toString: String = s"[${packets.mkString(",")}]"
  }
}
