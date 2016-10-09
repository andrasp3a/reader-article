package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.BaseSpec
import com.emarsys.readerarticle.model.{Item, TagCopyConfiguration}
import com.emarsys.readerarticle.storage.InMemoryStorage

import scala.concurrent.ExecutionContext.Implicits.global

class TagServiceSpec extends BaseSpec {

  import TagService._

  "Tag service" when {

    "adds a tag to an item" should {

      "add tag to untagged item" in {
        val storage = new InMemoryStorage {}
        addTagsToItem(Item("item", List("tag")), storage).futureValue
        storage.get("item").futureValue.get shouldEqual "tag"
      }

      "add tag to already tagged item" in {
        val storage = new InMemoryStorage {}

        val tagResult = for {
          _ <- addTagsToItem(Item("item", List("tag1", "tag2")), storage)
          tags <- storage.get("item")
        } yield tags.getOrElse("")

        tagResult.futureValue shouldEqual "tag1,tag2"
      }

    }

    "find tags of an item" should {

      "return empty sequence if item does not exists" in {
        val storage = new InMemoryStorage {}

        findTagsOfItem("nonExisting", storage).futureValue shouldEqual Seq()
      }

      "return tag names of existing item" in {
        val storage = new InMemoryStorage {}

        val tagResult = for {
          _ <- addTagsToItem(Item("item", List("tag1", "tag2")), storage)
          tags <- findTagsOfItem("item", storage)
        } yield tags

        tagResult.futureValue shouldEqual List("tag1", "tag2")
      }

    }

    "copy tags of item" should {

      "copy tags of existing item to an existing item" in {
        val storage = new InMemoryStorage {}

        val copyResult = for {
          _ <- addTagsToItem(Item("sourceItem", List("sourceTag1", "sourceTag2")), storage)
          _ <- addTagsToItem(Item("targetItem", List("tag1", "tag2")), storage)
          result <- copyTagsWithPrefix(TagCopyConfiguration("sourceItem", "targetItem"), storage)
        } yield result

        copyResult.futureValue shouldEqual true
        storage.get("targetItem").futureValue.get shouldEqual "tag1,tag2,sourceTag1,sourceTag2"
      }

    }

  }

}
