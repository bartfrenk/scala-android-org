package com.android.org.parser

case class Tree[+A](value: A, branches: List[Tree[A]]) {

  override def toString: String = {
    "\n" + traverse((a, i) => "\t" * i + a.toString + "\n")()
  }

  // this could be less ad-hoc (traversable?)
  def traverse(f: (A, Int) => String)(level: Int = 0): String = {
    def op(acc: String, tree: Tree[A]) = acc + tree.traverse(f)(level + 1)
    f(value, level) + branches.foldLeft("")(op)
  }

}

object Tree {
  def count[A](tree: Tree[A]): Int =
    scan(1)(tree)(_ + _)

  def scan[A, B](z: B)(tree: Tree[A])(f: (B, B) => B): B =
    tree.branches.foldLeft(z)((acc, branch) => f(acc, scan(z)(branch)(f)))

  /** Scan tree in depth first order, applying a monoid operation at each step. */
  def annotate[A, B](z: B)(tree: Tree[A])(op: (B, B) => B): Tree[(A, B)] = {
    val branches = tree.branches.map(branch => annotate(z)(branch)(op))
    val annotation = branches.foldLeft(z)((acc, branch) => branch.value match {
      case (_, b) => op(acc, b)
    })
    Tree((tree.value, annotation), branches)
  }
}
