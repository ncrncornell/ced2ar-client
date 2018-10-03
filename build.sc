import mill._
import mill.scalalib._
import mill.scalajslib._
import mill.scalalib.publish._

val scalaVersions = List("2.11.12", "2.12.7")
val mhtmlV = "0.4.0-RC1"

object client extends Module {

  class ClientJsModule(val crossScalaVersion: String)
      extends Ced2arClientModule
      with ScalaJSModule {
    def platformSegment = "js"
    def scalaJSVersion = "0.6.25"
    // currently sjs only:
    def ivyDeps = super.ivyDeps() ++ Agg(
      ivy"in.nvilla::monadic-html_sjs0.6:$mhtmlV"
    )

    object test extends ClientTestModule
  }
  object js extends Cross[ClientJsModule](scalaVersions: _*)

}

trait Ced2arClientModule
    extends CommonModule
    with CrossScalaModule
    with PublishModule {

  val modFolder: String = "client"
  def millSourcePath = build.millSourcePath / modFolder


  def artifactName = "ced2ar3-client"
  def publishVersion = "0.0.0"
  def pomSettings = PomSettings(
    description =
      """Comprehensive Extensible Data Documentation and
      | Access Repository (AAR) was designed to improve the documentation and
      | discoverability of both public and restricted data. Version 3 is a
      | modular implementaiton of CED2AR.""".stripMargin.replaceAll("\n", " "),
    organization = "edu.cornell.ncrn.ced2ar",
    url = "https://github.com/ncrncornell/ced2ar-client",
    licenses = Seq(License.`CC-BY-NC-SA-4.0`),
    versionControl = VersionControl.github("ncrncornell", "ced2ar-client"),
    developers = Seq(
      Developer("bbarker",
                "Brandon Barker",
                "https://www.cac.cornell.edu/barker/",
                Some("Cornell University")),
      Developer("larsvilhuber",
                "Lars Vilhuber",
                "https://www.ilr.cornell.edu/people/lars-vilhuber",
                Some("Cornell University")),
      Developer("CSimmer",
                "Charles C Simmer",
                "https://github.com/CSimmer",
                Some("Cornell University"))
    )
  )

  def ivyDeps = Agg(
   ivy"edu.cornell.ncrn.ced2ar::ced2ar3-api::0.0.3",
    ivy"in.nvilla::monadic-rx-cats::$mhtmlV",
    ivy"com.lihaoyi::upickle::0.4.4",
    ivy"fr.hmil::roshttp::2.0.1",
    ivy"io.monix::monix::2.2.3",
    ivy"com.github.japgolly.scalacss::core::0.5.3"
  )

  trait ClientTestModule extends Tests with CommonModule {
    def platformSegment = Ced2arClientModule.this.platformSegment
    def ivyDeps = Agg(
       ivy"org.scalatest::scalatest:3.0.1"
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }

  
}


trait CommonModule extends ScalaModule {
  
  def platformSegment: String

  def sources = T.sources(
    millSourcePath / platformSegment / "src"  ,
    millSourcePath / "shared" / "src"  ,
  )

  def scalacOptions = Seq(
    "-Ypartial-unification",
    "-language:higherKinds",
    "-deprecation",
    "-feature"
  )
   
  

  /*
  def scalacPluginIvyDeps = super.scalacPluginIvyDeps() ++ Agg(
    ivy"org.spire-math::kind-projector:$kindProjectorVersion",
    ivy"org.scalamacros:::paradise:$macroParadiseVersion"
  )
  */
}