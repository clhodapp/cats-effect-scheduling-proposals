
package demo

import cats.effect.IO
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

object Main {

  implicit def extendIO(io: IO.type): IOExtensions.type = IOExtensions

  val blocking = ExecutionContext.fromExecutor(
    Executors.newFixedThreadPool(1, NamedGroupThreadFactory("blocking"))
  )

  val compute = ExecutionContext.fromExecutor(
    Executors.newFixedThreadPool(1, NamedGroupThreadFactory("compute"))
  )

  val threadName = IO(Thread.currentThread().getName)
  def println(str: String) = IO(scala.Predef.println(str))
  val thirdPartyCall = IO.async[Unit] { cb =>
    new Thread(
      { () =>
        // third party library stuff on some thread we don't manage
        cb(Right(()))
      }
    ).start
  }

  def main(args: Array[String]): Unit = pureMain(args.toList).unsafeRunSync()

  def pureMain(args: List[String]): IO[Unit] = for {

    _ <- IO.shift(compute)
    a <- threadName
    _ <- println(s"a: $a")

    _ <- IO.shift(blocking)
    b <- threadName
    _ <- IO.shift(compute)
    _ <- println(s"b: $b")

    c <- threadName
    _ <- println(s"c: $c")

    _ <- thirdPartyCall
    _ <- IO.shift(compute)
    d <- threadName
    _ <- println(s"d: $d")

  } yield ()

}
