package com.emarsys.readerarticle.service

import cats.data.ReaderT
import cats.implicits._
import com.emarsys.readerarticle.model._
import com.emarsys.readerarticle.storage.Storage
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TagServiceReader {

  type DB[A] = ReaderT[Future, Storage, A]

  def copyTagsWithPrefix(copyConfig: TagCopyConfiguration): DB[Boolean] = {
    for {
      tags <- findTagsOfItem(copyConfig.sourceItem)
      itemWithTagsToAdd = Item(copyConfig.targetItem, filterByPrefix(tags, copyConfig.prefix))
      result <- addTagsToItem(itemWithTagsToAdd)
    } yield result
  }

  private def filterByPrefix(tags: List[String], prefix: Option[String]) = {
    tags.filter(tagName => prefix.fold(true)(prefix => tagName.startsWith(prefix)))
  }

  def findTagsOfItem(itemName: String): DB[List[String]] = withStorage { storage =>
    for {
      maybeContent <- storage.get(itemName)
    } yield maybeContent.fold(List.empty[String])(createList)
  }

  def addTagsToItem(item: Item): DB[Boolean] = withStorage { storage =>
    for {
      maybeContent <- storage.get(item.name)
      givenTags = item.tags.mkString(",")
      newTags = maybeContent.fold(givenTags)(_ + "," + givenTags)
      result <- storage.set(item.name, newTags)
    } yield result
  }

}
