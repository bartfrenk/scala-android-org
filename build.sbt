import android.Keys._

android.Plugin.androidBuild

name := "android-org"

scalaVersion := "2.11.7"

javacOptions in Compile ++= Seq("-source", "1.6", "-target", "1.6")

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.0"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"

// allows incremental multidex
minSdkVersion in Android := "21"

proguardScala in Android := true

useProguard in Android := true

proguardCache in Android := Seq.empty

dexMaxHeap in Android := "2048m"

dexMulti in Android := true
