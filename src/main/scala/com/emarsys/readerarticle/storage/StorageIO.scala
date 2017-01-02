package com.emarsys.readerarticle.storage

import akka.actor.ActorSystem
import com.emarsys.readerarticle.Config.redisConfig
import redis.RedisClient
import scala.concurrent.Future


trait StorageIO[F[_]] {
  def set(key: String, value: String): F[Boolean]
  def get(key: String): F[Option[String]]
}

object StorageIO {

  object syntax {
    def set[F[_]](key: String, value: String)(implicit ev: StorageIO[F]) = ev.set(key, value)

    def get[F[_]](key: String)(implicit ev: StorageIO[F]) = ev.get(key)
  }

  implicit def storageInstance(implicit system: ActorSystem) = new StorageIO[Future] {

    protected lazy val client = RedisClient(redisConfig.host, redisConfig.port, redisConfig.password)

    def set(key: String, value: String): Future[Boolean] = client.set(key, value)

    def get(key: String): Future[Option[String]] = client.get[String](key)

  }
}
