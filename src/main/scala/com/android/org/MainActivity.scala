package com.android.org

import android.app.Activity
import android.os.Bundle
import android.widget.FrameLayout
import android.util.Log

import TypedResource._

class MainActivity extends Activity with TypedFindView {
  override def onCreate(b: Bundle) {
    super.onCreate(b)
    setContentView(R.layout.main)
    findView(TR.label).setText("Hello world in Scala!!")
  }

}
