import sbt._

object Resolvers {
	val gdsMavenRepo = Resolver.file("GDS Maven Repo", new File("/srv/maven"))
}
