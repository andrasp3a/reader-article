package com.emarsys.readerarticle

import cats.implicits._
import akka.actor.ActorSystem
import com.emarsys.readerarticle.model.{Item, TagCopyConfiguration}
import com.emarsys.readerarticle.service.TagServiceReader
import com.emarsys.readerarticle.storage.Storage
import scala.concurrent.ExecutionContext.Implicits.global

object MainReader extends App {

  import TagServiceReader._

  val system = ActorSystem("storage-system")
  val storage = Storage(system)

  val tags = for {
    _ <- addTagsToItem(Item("hammer", List("heavy", "very dangerous")))
    _ <- addTagsToItem(Item("chainsaw", List("expensive")))
    _ <- copyTagsWithPrefix(TagCopyConfiguration("hammer", "chainsaw", Some("very")))
    tagsOfChainsaw <- findTagsOfItem("chainsaw")
  } yield tagsOfChainsaw.mkString("\n")

  tags.run(storage).foreach(println)

}
