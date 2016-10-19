package com.emarsys.readerarticle.service

import cats.data.ReaderT
import cats.implicits._
import com.emarsys.readerarticle.model._
import com.emarsys.readerarticle.storage.Storage
import TagTransformation._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TagServiceReader {

  type DB[A] = ReaderT[Future, Storage, A]

  def copyTagsWithPrefix(copyConfig: TagCopyConfiguration): DB[Boolean] = {
    for {
      sourceTags <- findTagsOfItem(copyConfig.sourceItem)
      itemWithTagsToAdd = Item(copyConfig.targetItem, filterByPrefix(sourceTags, copyConfig.prefix))
      result <- addTagsToItem(itemWithTagsToAdd)
    } yield result
  }

  def findTagsOfItem(itemName: String): DB[List[String]] = withStorage { storage =>
    for {
      maybeContent <- storage.get(itemName)
    } yield createTagList(maybeContent)
  }

  def addTagsToItem(item: Item): DB[Boolean] = withStorage { storage =>
    for {
      maybeContent <- storage.get(item.name)
      newTags = mergeTags(maybeContent, item.tags)
      result <- storage.set(item.name, newTags)
    } yield result
  }

}
