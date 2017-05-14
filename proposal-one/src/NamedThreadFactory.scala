package demo

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

case class NamedGroupThreadFactory(nameBase: String) extends ThreadFactory {
  val idGen = new AtomicInteger(1)
  def newThread(r: Runnable): Thread = {
    val thread = new Thread(r, nameBase ++ "-" ++ idGen.getAndIncrement.toString)
    thread.setDaemon(true)
    thread
  }
}
