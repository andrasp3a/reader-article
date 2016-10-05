package com.emarsys.readerarticle

import com.typesafe.config.ConfigFactory

trait Config {
  private val config = ConfigFactory.load()

  object redisConfig {
      val host = config.getString("redis.host")
      val port = config.getInt("redis.port")
      private val passwordOrEmpty = config.getString("redis.password")
      val password = if (passwordOrEmpty.isEmpty) None else Some(passwordOrEmpty)
  }
}

object Config extends Config
