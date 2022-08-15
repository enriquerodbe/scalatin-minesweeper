lazy val commonSettings = Seq(
  organization := "io.scalac",
  scalaVersion := "2.13.8",
  scalacOptions += "-Xsource:3",
  ThisBuild / scapegoatVersion := "1.4.15",
  wartremoverErrors ++= Warts.unsafe,
  coverageFailOnMinimum := true,
  coverageMinimumStmtTotal := 100,
  coverageMinimumBranchTotal := 100
)

lazy val api =
  project
    .in(file("api"))
    .settings(commonSettings)
    .settings(
      name := "minesweeper-api",
      libraryDependencies ++= Seq(
        "org.scalameta" %% "munit" % "0.7.29" % Test
      )
    )

lazy val squared =
  project
    .in(file("squared"))
    .settings(commonSettings)
    .dependsOn(api % "test->test;compile->compile")
    .settings(
      name := "minesweeper-squared"
    )

addCommandAlias("checkFormat", ";scalafmtSbtCheck ;scalafmtCheckAll")
addCommandAlias("lint", ";compile ;scapegoat")
addCommandAlias("testCoverage", ";coverage ;test ;coverageReport")
addCommandAlias("verify", ";checkFormat ;lint ;testCoverage")
