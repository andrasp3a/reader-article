package com.emarsys.readerarticle

import akka.actor.ActorSystem
import com.emarsys.readerarticle.model.Item
import com.emarsys.readerarticle.service.TagServiceTypeConstructor
import cats.implicits._


object MainTypeConstructor {

  implicit val system = ActorSystem("storage-system")
  implicit val executor = system.dispatcher

  val item = Item("hammer", List("heavy", "very dangerous"))
  TagServiceTypeConstructor.addTagsToItem(item)

}
