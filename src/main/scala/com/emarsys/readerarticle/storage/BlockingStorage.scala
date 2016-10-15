package com.emarsys.readerarticle.storage

trait BlockingStorage {

  def set(key: String, value: String): Boolean = true

  def get(key: String): Option[String] = Some("value")

}
