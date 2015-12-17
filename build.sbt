import android.Keys._

android.Plugin.androidBuild

name := "android-org"

scalaVersion := "2.11.2"

javacOptions in Compile ++= Seq("-source", "1.6", "-target", "1.6")
