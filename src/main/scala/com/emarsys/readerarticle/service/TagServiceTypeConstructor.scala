package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.model.Item
import TagTransformation._
import com.emarsys.readerarticle.storage.StorageIO
import cats._
import cats.implicits._

object TagServiceTypeConstructor {

  import StorageIO.syntax._

  def addTagsToItem[F[_]: StorageIO : Monad](item: Item): F[Boolean] = {
    for {
      maybeContent <- get(item.name)
      newTags = mergeTags(maybeContent, item.tags)
      result <- set(item.name, newTags)
    } yield result
  }

}
