package demo

import cats.effect.IO
import scala.concurrent.ExecutionContext


object IOExtensions {
  def shift(implicit ec: ExecutionContext): IO[Unit] = {
    IO.async[Unit] { cb =>
      ec.execute(() => cb(Right(())))
    }
  }
}
