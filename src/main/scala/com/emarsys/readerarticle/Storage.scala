package com.emarsys.readerarticle

import redis.RedisClient
import Config.redisConfig
import akka.actor.ActorSystem

import scala.concurrent.Future

trait Storage {

  implicit val system: ActorSystem

  protected lazy val client = RedisClient(redisConfig.host, redisConfig.port, redisConfig.password)

  def set(key: String, value: String): Future[Boolean] = client.set(key, value)

  def get(key: String): Future[Option[String]] = client.get[String](key)

}
