package com.android.org.app

import android.widget.ListAdapter
import android.view.View
import android.content.Context
import android.widget.Adapter
import android.widget.TextView
import android.view.ViewGroup
import android.util.Log
import android.database.DataSetObserver
import com.android.org.parser.MutableTree
import com.android.org.parser.OrgNode

/** Adapter that represents a [[com.android.org.parser.Tree]] as a list.
  * @constructor create a new adapter for a specific tree
  * @param context the current context
  * @param tree tree representation of the data to wrap
  * @param res maps node values of the tree to resource id's
  */
class OrgTreeAdapter(val context: Context, tree: MutableTree[OrgNode], val res: OrgNode => Int) extends ListAdapter {
  val LOG_TAG = "OrgTreeAdapter"

  def undefined = throw new Exception("not implemented")

  def getCount: Int = tree.count
  def getItem(position: Int): Object = tree.get(position)

  // every item is selectable
  def areAllItemsEnabled: Boolean = true
  def isEnabled(position: Int): Boolean = true

  def getItemId(position: Int): Long = position
  def getItemViewType(position: Int): Int = Adapter.IGNORE_ITEM_VIEW_TYPE
  def getView(position: Int, convertView: View, group: ViewGroup): View = {
    (convertView, tree.get(position)) match {
      case (_, None) => {
        Log.e(LOG_TAG, "no position " + position + " in org tree")
        throw new Exception()
      }
      case (null, Some(subtree)) => {
        val view = new TextView(context)
        view.setText("bla")
        view
      }
      case (view: TextView, Some(subtree)) => {
        view.setText("blabla")
        view
      }
      case _ => {
        Log.e(LOG_TAG, "expected a text view")
        throw new Exception()
      }
    }
  }

  def getViewTypeCount(): Int = 1
  def hasStableIds: Boolean = false
  // adapter is considered to be empty when it adapts the minimal non-empty tree
  def isEmpty: Boolean = (getCount == 1)
  def registerDataSetObserver(obs: DataSetObserver): Unit = undefined
  def unregisterDataSetObserver(obs: DataSetObserver): Unit = undefined
}
