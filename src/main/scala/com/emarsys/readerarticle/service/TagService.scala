package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.model._
import com.emarsys.readerarticle.storage.Storage
import TagTransformation._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TagService {

  def copyTagsWithPrefix(copyConfig: TagCopyConfiguration, storage: Storage) = {
    for {
      sourceTags <- findTagsOfItem(copyConfig.sourceItem, storage)
      itemWithTagsToAdd = Item(copyConfig.targetItem, filterByPrefix(sourceTags, copyConfig.prefix))
      result <- addTagsToItem(itemWithTagsToAdd, storage)
    } yield result
  }

  def findTagsOfItem(itemName: String, storage: Storage): Future[List[String]] = {
    for {
      maybeContent <- storage.get(itemName)
    } yield createTagList(maybeContent)
  }

  def addTagsToItem(item: Item, storage: Storage): Future[Boolean] = {
    for {
      maybeContent <- storage.get(item.name)
      newTags = mergeTags(maybeContent, item.tags)
      result <- storage.set(item.name, newTags)
    } yield result
  }

}
