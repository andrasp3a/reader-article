package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.model.{Item, TagCopyConfiguration}
import com.emarsys.readerarticle.storage.BlockingStorage

object TagServiceBlocking {

  def copyTagsWithPrefix(copyConfig: TagCopyConfiguration, storage: BlockingStorage) = {
    val tags = findTagsOfItem(copyConfig.sourceItem, storage)
    val itemWithTagsToAdd = Item(copyConfig.targetItem, filterByPrefix(tags, copyConfig.prefix))
    addTagsToItem(itemWithTagsToAdd, storage)
  }

  private def filterByPrefix(tags: List[String], prefix: Option[String]) = {
    tags.filter(tagName => prefix.fold(true)(prefix => tagName.startsWith(prefix)))
  }

  def findTagsOfItem(itemName: String, storage: BlockingStorage): List[String] = {
    val maybeContent = storage.get(itemName)
    maybeContent.fold(List.empty[String])(_.split(",").map(_.trim).toList)
  }

  def addTagsToItem(item: Item, storage: BlockingStorage): Boolean = {
    val maybeContent = storage.get(item.name)
    val givenTags = item.tags.mkString(",")
    val newTags = maybeContent.fold(givenTags)(_ + "," + givenTags)
    storage.set(item.name, newTags)
  }

}
