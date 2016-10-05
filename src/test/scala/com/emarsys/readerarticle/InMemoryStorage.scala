package com.emarsys.readerarticle

import akka.actor.ActorSystem
import scala.collection.mutable
import scala.concurrent.Future

trait InMemoryStorage extends Storage {

  implicit val system = ActorSystem("test-system")

  lazy val storage = mutable.Map.empty[String, String]

  override def set(key: String, value: String): Future[Boolean] = {
    storage(key) = value
    Future.successful(true)
  }

  override def get(key: String): Future[Option[String]] = Future.successful(storage.get(key))

}
