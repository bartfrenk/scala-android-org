package com.android.org.app

import android.app.Activity
import android.os.Bundle

import com.android.org.R
import com.android.org.TR
import com.android.org.TypedFindView

class MainActivity extends Activity with TypedFindView {
  override def onCreate(b: Bundle) {

    super.onCreate(b)
    setContentView(R.layout.main)
    findView(TR.label).setText("Hello world in Scala!!")
  }

}
