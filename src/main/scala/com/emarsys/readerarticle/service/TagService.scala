package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.model.Tag
import com.emarsys.readerarticle.storage.Storage
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TagService {

  def addTagToItem(tag: Tag, storage: Storage): Future[Boolean] = {
    for {
      maybeContent <- storage.get(tag.itemName)
      newTags = maybeContent.fold(tag.tagName)(_ + ", " + tag.tagName)
      result <- storage.set(tag.itemName, newTags)
    } yield result
  }

}
