package com.emarsys.readerarticle

import cats.data.{Kleisli, ReaderT}
import cats.implicits._
import com.emarsys.readerarticle.storage.Storage

import scala.concurrent.Future

package object service {

  val createList: (String => List[String]) = s => s.split(",").map(_.trim).toList

  def withStorage[A](f: Storage => Future[A]): ReaderT[Future, Storage, A] = Kleisli(f)

}
