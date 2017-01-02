package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.BaseSpec
import com.emarsys.readerarticle.model.Item
import com.emarsys.readerarticle.storage.InMemoryStorageInstanceFactory


class TagServiceTypeConstructorSpec extends BaseSpec with InMemoryStorageInstanceFactory {

  import TagServiceTypeConstructor._

  trait InMemoryStorageScope {
    implicit val storageInstance = createStorage
  }

  "Tag service - with type constructor classes" when {

    "adds a tag to an item" should {

      "add tag to untagged item" in new InMemoryStorageScope {
        addTagsToItem(Item("item", List("tag")))
        storageInstance.get("item").get shouldEqual "tag"
      }

      "add tag to already tagged item" in new InMemoryStorageScope {
        addTagsToItem(Item("item", List("tag1", "tag3")))
        addTagsToItem(Item("item", List("tag1", "tag2")))
        storageInstance.get("item").get shouldEqual "tag1,tag3,tag2"
      }

    }

  }
}
