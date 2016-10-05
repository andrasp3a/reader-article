package com.emarsys.readerarticle

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

class InMemoryStorageSpec extends WordSpec with Matchers with ScalaFutures {

  "InMemoryStorage" should {

    "be able to store and retrieve value by key" in new InMemoryStorage {
      set("testKey", "testValue")
      get("testKey").futureValue.get shouldEqual "testValue"
    }

    "return none for not defined key" in new InMemoryStorage {
      get("testKey").futureValue shouldEqual None
    }

  }

}
