package com.android.org.app

import android.app.Activity
import android.os.Bundle
import android.widget.ListView

import com.android.org.parser._

import com.android.org.R
import com.android.org.TR
import com.android.org.TypedFindView

class MainActivity extends Activity with TypedFindView {
  override def onCreate(b: Bundle) {

    val testOrgString = "* A\n** B\nasdsadsad\nasdsad"

    super.onCreate(b)
    setContentView(R.layout.main)
    findView(TR.label).setText("Hello world in Scala!!")
    val orgView = findView(TR.org_tree) match {
      case view: ListView => view
      case _ => throw new Exception()
    }

    val result = parseOrgString(testOrgString)

    result match {
      case Success(tree) => {
        val adapter = new OrgTreeAdapter(this.getApplicationContext(), tree)
        orgView.setAdapter(adapter)
      }
    }

  }

  // TODO should be moved to the parser module
  def parseOrgString(input: String): Result[MutableTree[OrgNode]] = {
    OrgParsers.run(OrgParsers.doc())(input) match {
      case Success(tree) => Success(MutableTree.convert(tree))
      case fail @ Failure(_) => fail
    }
  }

}
