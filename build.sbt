
assemblyJarName in assembly := "__.jar"

// mainClass in assembly := Some("com.__.Main")

name := "__"

organization := "com.dougybarbo"

version := "0.1"

crossScalaVersions := Seq("2.10.4", "2.11.7")

scalaHome := Some(file("/usr/local/bin/scala"))

sourceDirectory := new File(baseDirectory.value, "src")

unmanagedBase := baseDirectory.value / "lib"

libraryDependencies ++= Seq(
	"org.scalatest" 		%%		"scalatest" 			%	"3.0.0-SNAP5"	 	% "test",
	"org.scalaz"			%%		"scalaz-core"			%	"7.2.0-M2",
	"org.scalanlp"			%%		"breeze"				%	"0.11.2",
	"com.quantifind"		%% 		"wisp"					%	"0.0.4",
	"org.scalikejdbc"		%% 		"scalikejdbc"			%	"2.2.7",
	"com.h2database"		%			"h2"					%	"1.4.187",
	"ch.qos.logback"		% 			"logback-classic"		%	"1.1.3",
	"org.apache.hadoop"	%			"hadoop-client"			%	"2.7.1"
)

resolvers ++= Seq(
	"Akka Repository"         			at "http://repo.akka.io/releases/",
	"Sonatype OSS Snapshots"  		at "http://oss.sonatype.org/content/repositories/snapshots/"
)

// resolvers += Resolver.file("my-test-repo", file("test")) transactional()

javacOptions ++= Seq(
	"-Xlint:unchecked",
	"-Xlint:deprecation",
	"-Xmx4096m",
	"-Xms512m",
	"-XX:MaxPerSize=512"
)

// test debug output gets mangled otherwise
parallelExecution in Test := false

scalacOptions := Seq("-Yrepl-sync")

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
	{
		case "application.conf" => MergeStrategy.concat
		case "reference.conf" => MergeStrategy.concat
		case "META-INF/spring.tooling" => MergeStrategy.concat
		case "overview.html" => MergeStrategy.rename
		case x => old(x)
	}
}

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
	cp filter { f =>
		(f.data.getName contains "commons-logging") ||
		(f.data.getName contains "sbt-link")
	}
}
