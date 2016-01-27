package com.android.org.parser

import org.scalatest._
import prop._
import org.scalacheck.Gen
import scala.collection.mutable.{Seq => BranchList}

import scala.language.postfixOps

class TreeFunSuite extends FunSuite with PropertyChecks with Matchers {
  import Tree._
  import MutableTree._

  def emptyTree[A] = scala.collection.mutable.Seq.empty[MutableTree[A]]

  val mutableTree =
    MutableTree(0, BranchList(
      MutableTree(1, BranchList(
        MutableTree(2, emptyTree),
        MutableTree(3, emptyTree),
        MutableTree(4, emptyTree))),
      MutableTree(5, emptyTree),
      MutableTree(6, BranchList(
        MutableTree(7, BranchList(
          MutableTree(8, emptyTree),
          MutableTree(9, emptyTree))),
        MutableTree(10, emptyTree)))))


  test("Linear indexing of mutable tree should be depth first") {
    (0 until 10).foreach {index =>
      mutableTree.get(index).get.value should equal (index)
    }
  }

  test("Mutable tree values should be mutable") {
    val index = 6
    val after = 60
    mutableTree.get(index).get.value = after
    mutableTree.get(index).get.value should equal (after)
  }
}
