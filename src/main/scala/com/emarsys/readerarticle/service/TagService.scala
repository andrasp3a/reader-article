package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.model._
import com.emarsys.readerarticle.storage.Storage
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TagService {

  def copyTagsWithPrefix(copyConfig: TagCopyConfiguration, storage: Storage) = {
    for {
      tags <- findTagsOfItem(copyConfig.sourceItem, storage)
      itemWithTagsToAdd = Item(copyConfig.targetItem, filterByPrefix(tags, copyConfig.prefix))
      result <- addTagsToItem(itemWithTagsToAdd, storage)
    } yield result
  }

  private def filterByPrefix(tags: List[String], prefix: Option[String]) = {
    tags.filter(tagName => prefix.fold(true)(prefix => tagName.startsWith(prefix)))
  }

  def findTagsOfItem(itemName: String, storage: Storage): Future[List[String]] = {
    for {
      maybeContent <- storage.get(itemName)
    } yield maybeContent.fold(List.empty[String])(_.split(",").map(_.trim).toList)
  }

  def addTagsToItem(item: Item, storage: Storage): Future[Boolean] = {
    for {
      maybeContent <- storage.get(item.name)
      givenTags = item.tags.mkString(",")
      newTags = maybeContent.fold(givenTags)(_ + "," + givenTags)
      result <- storage.set(item.name, newTags)
    } yield result
  }

}
