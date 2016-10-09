package com.emarsys.readerarticle

import akka.actor.ActorSystem
import com.emarsys.readerarticle.model.{Item, TagCopyConfiguration}
import com.emarsys.readerarticle.service.{TagService, TagServiceCurry}
import com.emarsys.readerarticle.storage.Storage

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  import TagService._

  val system = ActorSystem("storage-system")
  val storage = Storage(system)

  val tags = for {
    _ <- addTagsToItem(Item("hammer", List("heavy", "very dangerous")), storage)
    _ <- addTagsToItem(Item("chainsaw", List("expensive")), storage)
    _ <- copyTagsWithPrefix(TagCopyConfiguration("hammer", "chainsaw", Some("very")), storage)
    tagsOfChainsaw <- findTagsOfItem("chainsaw", storage)
  } yield tagsOfChainsaw.mkString("\n")

  tags.foreach(println)

  TagServiceCurry.findTagsOfItem("hammer").apply(storage).foreach(println)

}
