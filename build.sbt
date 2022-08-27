lazy val commonSettings = Seq(
  organization := "io.scalac",
  scalaVersion := "2.13.8",
  scalacOptions += "-Xsource:3",
  ThisBuild / scapegoatVersion := "1.4.15",
  wartremoverErrors ++= Warts.unsafe.diff(Seq(Wart.Any)),
  coverageFailOnMinimum := true,
  coverageMinimumStmtTotal := 100,
  coverageMinimumBranchTotal := 100,
  coverageExcludedPackages := "io.scalac.minesweeper.cli*"
)

lazy val api =
  project
    .in(file("api"))
    .settings(commonSettings)
    .settings(
      name := "minesweeper-api",
      libraryDependencies ++= Seq(
        "org.scalameta" %% "munit" % "0.7.29" % Test,
        "org.scalameta" %% "munit-scalacheck" % "0.7.29" % Test
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

lazy val cli =
  project
    .in(file("cli"))
    .settings(commonSettings)
    .dependsOn(api % "test->test;compile->compile")
    .settings(
      name := "minesweeper-cli",
      libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.14"
    )

lazy val `cli-squared` =
  project
    .in(file("cli-squared"))
    .settings(commonSettings)
    .dependsOn(cli, squared)
    .settings(
      name := "minesweeper-cli-squared"
    )

addCommandAlias("checkFormat", ";scalafmtSbtCheck ;scalafmtCheckAll")
addCommandAlias("lint", ";compile ;scapegoat")
addCommandAlias("testCoverage", ";coverage ;test ;coverageReport")
addCommandAlias("mutationTest", ";project squared ;stryker")
addCommandAlias("verify", ";checkFormat ;lint ;testCoverage; mutationTest")
