package org.adventofcode.day13

import org.adventofcode.ResourceLoader

object Day13B {

  def main(): Int = {

    val dividerPacket2 = PacketList(List(PacketList(List(Signal(2)))))
    val dividerPacket6 = PacketList(List(PacketList(List(Signal(6)))))

    ResourceLoader.processLines(
      _.filterNot(_.isEmpty)
        .map(convertToPacket)
        .concat(Iterator(dividerPacket2, dividerPacket6))
        .toList
        .sorted
        .zipWithIndex
        .foldLeft(new DecodedKeyFinder(dividerPacket2, dividerPacket6)) { case (finder, (packet, index)) =>
          finder.append(packet, index)
        }
        .key
    )
  }

  private class DecodedKeyFinder(
                                  val dividerPacket2: PacketList,
                                  val dividerPacket6: PacketList,
                                ) {

    private var dividerPacket2Index: Option[Int] = None
    private var dividerPacket6Index: Option[Int] = None

    def append(packet: Packet, index: Int): DecodedKeyFinder = {
      if (packet == dividerPacket2) {
        dividerPacket2Index = Some(index + 1) // index starts with 1
      } else if (packet == dividerPacket6) {
        dividerPacket6Index = Some(index + 1) // index starts with 1
      }
      this
    }

    def key: Int = dividerPacket2Index.get * dividerPacket6Index.get
  }
}
