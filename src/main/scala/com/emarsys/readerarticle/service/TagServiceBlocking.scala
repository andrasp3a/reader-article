package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.model.{Item, TagCopyConfiguration}
import com.emarsys.readerarticle.storage.BlockingStorage
import TagTransformation._

object TagServiceBlocking {









  def copyTagsWithPrefix(copyConfig: TagCopyConfiguration, storage: BlockingStorage): Boolean = {
    val sourceTags = findTagsOfItem(copyConfig.sourceItem, storage)
    val itemWithTagsToAdd = Item(copyConfig.targetItem, filterByPrefix(sourceTags, copyConfig.prefix))
    addTagsToItem(itemWithTagsToAdd, storage)
  }













  def findTagsOfItem(itemName: String, storage: BlockingStorage): List[String] = {
    val maybeContent = storage.get(itemName)
    createTagList(maybeContent)
  }













  def addTagsToItem(item: Item, storage: BlockingStorage): Boolean = {
    val maybeContent = storage.get(item.name)
    val newTags = mergeTags(maybeContent, item.tags)
    storage.set(item.name, newTags)
  }


}
