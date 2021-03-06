package com.emarsys.readerarticle.service

import com.emarsys.readerarticle.storage.Storage
import TagTransformation._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TagServiceCurry {

  def findTagsOfItem(itemName: String): Storage => Future[List[String]] = storage => {
    for {
      maybeContent <- storage.get(itemName)
    } yield createTagList(maybeContent)
  }

}
