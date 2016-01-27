package com.android.org.parser

object Tree {
  def count[A](tree: Tree[A]): Int =
    scan(1)(tree)(_ + _)

  def scan[A, B](z: B)(tree: Tree[A])(f: (B, B) => B): B =
    tree.branches.foldLeft(z)((acc, branch) => f(acc, scan(z)(branch)(f)))

  def compose[A, B](z: A => B)(tree: Tree[A])(op: (B, B) => B): Tree[B] = {
    val branches = tree.branches.map(branch => compose(z)(branch)(op))
    val value = branches.foldLeft(z(tree.value))((acc, branch) => op(acc, branch.value))
    Tree(value, branches)
  }

  def annotate[A, B](z: B)(tree: Tree[A])(op: (B, B) => B): Tree[(A, B)] =
    compose((a: A) => (a, z))(tree) {
      case ((a, m), (_, n)) => (a, op(m, n))
    }

}

case class Tree[+A](value: A, branches: Seq[Tree[A]]) {

  override def toString: String = {
    "\n" + traverse((a, i) => "\t" * i + a.toString + "\n")()
  }

  // this could be less ad-hoc (traversable?)
  def traverse(f: (A, Int) => String)(level: Int = 0): String = {
    def op(acc: String, tree: Tree[A]) = acc + tree.traverse(f)(level + 1)
    f(value, level) + branches.foldLeft("")(op)
  }

}

/** Tree with mutable values and children that is linearly indexable.
  * The linear indexing is in depth-first order. */
class MutableTree[A](var value: A, val branches: MutableTree.BranchList[A]) {
  import MutableTree._

  var count: Int = branches.foldLeft(1)((n, branch) => n + branch.count)

  /** Returns the subtree indexed by [[index]]. */
  def get(index: Int): Option[MutableTree[A]] =
    if (index < 0) None
    else if (index == 0) Some(this)
    else selectChildWithSubtree(index - 1) match {
      case Some((child, remainder)) => child.get(remainder)
      case None => None
    }

  /** Returns the child in which the subtree indexed by [[index]] lives.
    * @return a pair consisting of the child and the relative index of the
    * to be selected subtree within that child, or `None` if there is
    * no such subtree. */
  private def selectChildWithSubtree(index: Int): Option[(MutableTree[A], Int)] = {
    def selectChildRec(branches: BranchList[A], remainder: Int): Option[(MutableTree[A], Int)] = {
      if (branches.isEmpty) None
      else if (remainder < branches.head.count) Some((branches.head, remainder))
      else selectChildRec(branches.tail, remainder - branches.head.count)
    }
    selectChildRec(branches, index)
  }


}

object MutableTree {
  type BranchList[A] = scala.collection.mutable.Seq[MutableTree[A]]

  /** Creates a mutable tree. */
  def apply[A](value: A, branches: scala.collection.mutable.Seq[MutableTree[A]]) =
    new MutableTree(value, branches)

  /** Converts a tree to a mutable tree. */
  def convert[A](tree: Tree[A]): MutableTree[A] =
    MutableTree(tree.value, tree.branches.map(convert)(collection.breakOut))

  /** Creates a [[MutableTree]] node without children. */
  def leaf[A](value: A) = new MutableTree(value, scala.collection.mutable.Seq.empty[MutableTree[A]])

}
