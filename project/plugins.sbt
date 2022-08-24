addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.1")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.0.5")
addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.1.1")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.1")
addSbtPlugin("io.stryker-mutator" % "sbt-stryker4s" % "0.14.3")

/*
There's a version conflict between transitive dependencies
 * org.scala-lang.modules:scala-xml_2.12:2.1.0 (early-semver) is selected over 1.0.6
  +- org.scoverage:scalac-scoverage-reporter_2.12:2.0.1 (depends on 2.1.0)
  +- org.scala-lang:scala-compiler:2.12.16              (depends on 1.0.6)
 */
dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"
