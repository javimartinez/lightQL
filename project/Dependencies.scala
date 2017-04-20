import sbt._

object Version {
  final val ScalaVersions       = Seq("2.11.8", "2.12.1")
  final val ScalaTest           = "3.0.1"
  final val FastParse           = "0.4.2"
  final val Elastic4s           = "5.2.10" // DO NOT UPGRADE TO 5.2.11 (see https://github.com/sksamuel/elastic4s/issues/819)
  final val NscalaTime          = "2.16.0"
  final val Dsl                 = "0.1.10-SNAPSHOT"
  final val ElasticMaterializer = "5.0.9-SNAPSHOT"
}

object Library {
  val fastParse        = "com.lihaoyi"            %% "fastparse"         % Version.FastParse
  val elastic4s        = "com.sksamuel.elastic4s" %% "elastic4s-core"    % Version.Elastic4s
  val elastic4sTcp     = "com.sksamuel.elastic4s" %% "elastic4s-tcp"     % Version.Elastic4s
  val elastic4sTestkit = "com.sksamuel.elastic4s" %% "elastic4s-testkit" % Version.Elastic4s
  val nscalaTime       = "com.github.nscala-time" %% "nscala-time"       % Version.NscalaTime
  val scalaTest        = "org.scalatest"          %% "scalatest"         % Version.ScalaTest
}
