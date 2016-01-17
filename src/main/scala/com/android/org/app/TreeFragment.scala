package com.android.org.app

import android.widget.ListAdapter
import android.view.View
import android.content.Context
import android.view.ViewGroup
import android.database.DataSetObserver
import com.android.org.parser.Tree

/** Adapter that represents a [[com.android.org.parser.Tree]] as a list.
  * @constructor create a new adapter for a specific tree
  * @param context the current context
  * @param tree tree representation of the data to wrap
  * @param res maps node values of the tree to resource id's
  */
class TreeAdapter[A](context: Context, tree: Tree[A], res: A => Int) extends ListAdapter {

  def undefined = throw new Exception("not implemented")

  def getCount: Int = undefined
  def getItem(index: Int): Object = undefined
  def areAllItemsEnabled: Boolean = undefined
  def isEnabled(index: Int): Boolean = undefined
  def getItemId(index: Int): Long = undefined
  def getItemViewType(index: Int): Int = undefined
  def getView(index: Int, view: View, group: ViewGroup): View = undefined
  def getViewTypeCount(): Int = undefined
  def hasStableIds: Boolean = undefined
  def isEmpty: Boolean = undefined
  def registerDataSetObserver(obs: DataSetObserver): Unit = undefined
  def unregisterDataSetObserver(obs: DataSetObserver): Unit = undefined
}
