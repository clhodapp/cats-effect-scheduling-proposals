
organization in ThisBuild := "net.clhodapp"
name in ThisBuild := "cats-effect-scheduling-proposals"

scalaVersion in ThisBuild := "2.12.2"

lazy val catsEffect = ProjectRef(
  uri("ssh://git@github.com:/typelevel/cats-effect.git#36a0fb6"),
  "coreJVM"
)

lazy val proposalOne =
  project
    .in(file("proposal-one"))
    .dependsOn(catsEffect)
    .settings(
      (scalaSource in Compile) := sourceDirectory.value
    )

scalacOptions += "-language:_"

