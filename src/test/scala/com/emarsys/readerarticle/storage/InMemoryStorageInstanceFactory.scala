package com.emarsys.readerarticle.storage

import cats._
import scala.collection.mutable


trait InMemoryStorageInstanceFactory {

  def createStorage = new StorageIO[Id] {

    lazy val storage = mutable.Map.empty[String, String]

    override def set(key: String, value: String): Id[Boolean] = {
      storage(key) = value
      true
    }

    override def get(key: String): Id[Option[String]] = storage.get(key)

  }

}
