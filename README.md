# ScaLatin Minesweeper

## Project structure

This is a [sbt](https://www.scala-sbt.org/) multi-module project with the following modules:
1. `api` - Contains only interfaces and specifications about the expected behavior of any
   implementation of the game.
2. `squared` - An implementation for the classic Minesweeper in 2D.

## Development environment

This project is built using sbt for Scala on the JVM. Both are needed in order to be able to build 
and run the code.

### Nix

This project provides a [Nix](https://nixos.org/) flake with a dev shell that declares the sbt 
dependency together with the right JDK. Using `nix develop` is all you need.
Optionally, if you also use [direnv](https://direnv.net/), there is a `.envrc` file so just using 
`direnv allow` will also work.

### Manual installation

If you don't use Nix, you have to install sbt and some JDK version 17. The sbt version will be 
automatically picked up from `project/build.properties` file no matter what version you have
installed.

## SBT commands

You can expect all the usual sbt commands to work, for example `compile`, `test`, or `api/compile`.
There are also some plugins configured that add their own commands. See the corresponding
documentation to find more about how they work:
- [tpolecat](https://github.com/typelevel/sbt-tpolecat) - Strict scalac options
- [Wartremover](https://www.wartremover.org/doc/install-setup.html) - Static linter
- [Scapegoat](https://github.com/scapegoat-scala/sbt-scapegoat) - Static linter
- [Scalafmt](https://scalameta.org/scalafmt/docs/installation.html#sbt) - Code formatter
- [Scoverage](https://github.com/scoverage/sbt-scoverage) - Code coverage
- [Stryker](https://stryker-mutator.io/docs/stryker4s/getting-started/) - Mutation testing

There are some sbt command alias provided as well:
- `checkFormat` - Check all Scala files to verify their formatting.
- `lint` - Compile with strict flags enabled plus wartremover checks and run scapegoat.
- `testCoverage` - Enable coverage, run test, generate a report, and verify that minimum code coverage
is reached.
- `mutationTest` - Introduce changes to the code, run tests, and verify that some test fails.
- `verify` - Run all the above sequentially.
