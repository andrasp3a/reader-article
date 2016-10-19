package com.emarsys.readerarticle.service

object TagTransformation {

  def filterByPrefix(tags: List[String], prefix: Option[String]) = {
    tags.filter(tagName => prefix.fold(true)(prefix => tagName.startsWith(prefix)))
  }

  def createTagList(maybeContent: Option[String]) = {
    maybeContent.map(content =>
      content.split(",").map(_.trim).toList
    ).getOrElse(List())
  }

  def mergeTags(maybeContent: Option[String], tagList: List[String]) = {
    val givenTags = tagList.mkString(",")
    maybeContent.fold(givenTags)(_ + "," + givenTags)
  }

}
