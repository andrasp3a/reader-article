package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.BaseSpec
import com.emarsys.readerarticle.model.Tag
import com.emarsys.readerarticle.storage.InMemoryStorage
import scala.concurrent.ExecutionContext.Implicits.global

class TagServiceSpec extends BaseSpec {

  import TagService._

  "Tag service" when {

    "adds a tag to an item" should {

      "add tag to untagged item" in {
        val storage = new InMemoryStorage {}
        addTagToItem(Tag("item", "tag"), storage).futureValue
        storage.get("item").futureValue.get shouldEqual "tag"
      }

      "add tag to already tagged item" in {
        val storage = new InMemoryStorage {}

        val tagResult = for {
          _ <- addTagToItem(Tag("item", "tag1"), storage)
          _ <- addTagToItem(Tag("item", "tag2"), storage)
          tags <- storage.get("item")
        } yield tags.getOrElse("")

        tagResult.futureValue shouldEqual "tag1, tag2"
      }

    }

  }

}
