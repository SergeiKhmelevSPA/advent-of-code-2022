package org.adventofcode

import scala.language.implicitConversions
import scala.util.matching.Regex

package object day7 extends HasFileName {
  override val fileName: String = "day7.txt"

  class FileSystem {

    val root: Directory = new Directory("/", parent = None)
    private var current: Directory = root

    def input(line: String): Unit = {
      val command: Command = line

      command match {
        case MoveToRoot() => current = root
        case MoveOut() => current = current.parent.get
        case MoveToDirectory(directory) => current = current.getChildByName(directory)
        case ListCommand() => /* do nothing for simplicity */
        case ListedFile(size, fileName) => current.addFile(size)
        case ListedDirectory(directory) => current.addDirectory(directory)
      }
    }
  }

  class Directory(
                   val name: String,
                   var size: Int = 0,
                   val parent: Option[Directory],
                   var children: Set[Directory] = Set.empty[Directory],
                 ) {

    def addDirectory(name: String): Unit =
      children += new Directory(name, parent = Some(this))

    def addFile(fileSize: Int): Unit = {
      size += fileSize
      parent.foreach(_.addFile(fileSize))
    }

    def getChildByName(name: String): Directory =
      children.find(_.name == name).get

    override def toString: String = s"Directory(name=$name, size=$size)"
  }

  private implicit def stringToCommand(value: String): Command = {
    val changeDirectoryPattern: Regex = """^\$ cd (.+)$""".r
    val listPattern: Regex = """^\$ ls$""".r
    val listedFilePattern: Regex = """^(\d+) ([\w.]+)$""".r
    val listedDirectoryPattern: Regex = """^dir (\w+)+$""".r

    value match {
      case changeDirectoryPattern(directory) => {
        directory match {
          case ".." => MoveOut()
          case "/" => MoveToRoot()
          case _ => MoveToDirectory(directory)
        }
      }
      case listPattern() => ListCommand()
      case listedFilePattern(size, fileName) => ListedFile(size.toInt, fileName)
      case listedDirectoryPattern(directory) => ListedDirectory(directory)
    }
  }

  trait Command

  trait ChangeDirectoryCommand extends Command

  case class MoveToDirectory(directory: String) extends ChangeDirectoryCommand {
    override def toString: String = s"MoveToDirectory: $directory"
  }

  case class MoveOut() extends ChangeDirectoryCommand

  case class MoveToRoot() extends ChangeDirectoryCommand

  case class ListCommand() extends Command

  case class ListedFile(size: Int, fileName: String) extends Command {
    override def toString: String = s"ListedFile(size=$size, fileName=$fileName)"
  }

  case class ListedDirectory(directory: String) extends Command {
    override def toString: String = s"ListedDirectory: $directory"
  }
}
