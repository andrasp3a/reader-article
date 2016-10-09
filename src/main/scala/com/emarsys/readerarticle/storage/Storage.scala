package com.emarsys.readerarticle.storage

import akka.actor.ActorSystem
import com.emarsys.readerarticle.Config.redisConfig
import redis.RedisClient

import scala.concurrent.Future

trait Storage {

  implicit val system: ActorSystem

  protected lazy val client = RedisClient(redisConfig.host, redisConfig.port, redisConfig.password)

  def set(key: String, value: String): Future[Boolean] = client.set(key, value)

  def get(key: String): Future[Option[String]] = client.get[String](key)

}

object Storage {
  def apply(actorSystem: ActorSystem) = new Storage {
    override implicit val system: ActorSystem = actorSystem
  }
}