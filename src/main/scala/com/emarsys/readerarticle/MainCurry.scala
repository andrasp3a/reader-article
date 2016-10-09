package com.emarsys.readerarticle

import akka.actor.ActorSystem
import com.emarsys.readerarticle.service.TagServiceCurry
import com.emarsys.readerarticle.storage.Storage
import scala.concurrent.ExecutionContext.Implicits.global

object MainCurry extends App {

  import TagServiceCurry._

  val system = ActorSystem("storage-system")
  val storage = Storage(system)

  findTagsOfItem("hammer")(storage).foreach(println)

}
