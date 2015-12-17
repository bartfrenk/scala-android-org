package com.android.org

import scala.collection.immutable.List

abstract class OrgNode
case class OrgHeading(text: String, level: Int) extends OrgNode
case class OrgComment(text: String) extends OrgNode
case class OrgText(text: String) extends OrgNode

trait Tree[+A] {
  import scala.annotation.tailrec

  def value: A

  def children: List[Tree[A]]
}
