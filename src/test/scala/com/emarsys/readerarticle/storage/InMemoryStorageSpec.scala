package com.emarsys.readerarticle.storage

import com.emarsys.readerarticle.BaseSpec

class InMemoryStorageSpec extends BaseSpec {

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
