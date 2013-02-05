import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "aeroclub"
    val appVersion      = "1.0"

    val appDependencies = Seq(
	    javaCore,
	    javaJdbc,
	    javaEbean,
      "mysql" % "mysql-connector-java" % "5.1.21"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(

    )

}
