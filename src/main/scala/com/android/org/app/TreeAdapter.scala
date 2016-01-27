package com.android.org.app

import android.widget.BaseAdapter
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

// TODO this should have a bound type parameter for better seperation of concerns
class OrgTreeAdapter(val context: Context, tree: MutableTree[OrgNode]) extends BaseAdapter {

  /** Tag under which this class writes to the Android logs. */
  val LOG_TAG: String = getClass.getSimpleName

  def getCount: Int = tree.count

  def getItem(position: Int): Object = tree.get(position)

  def getItemId(position: Int): Long = position

  def getView(position: Int, convertView: View, group: ViewGroup): View = {
    Log.e(LOG_TAG, s"obtaining view for position $position")
    (convertView, tree.get(position)) match {
      case (_, None) => {
        Log.e(LOG_TAG, "no position " + position + " in org tree")
        throw new Exception()
      }
      case (null, Some(subtree)) => {
        val view = new TextView(context)
        view.setText(subtree.value.mkString)
        view
      }
      case (view: TextView, Some(subtree)) => {
        view.setText(subtree.value.mkString)
        view
      }
      case _ => {
        Log.e(LOG_TAG, "expected a text view")
        throw new Exception()
      }
    }
  }

  // adapter is considered to be empty when it adapts the minimal non-empty tree
  override def isEmpty: Boolean = (getCount == 1)
}
