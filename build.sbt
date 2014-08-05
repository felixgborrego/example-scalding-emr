import AssemblyKeys._
import sbtassembly.Plugin.MergeStrategy

net.virtualvoid.sbt.graph.Plugin.graphSettings

assemblySettings

name := """example-scalding-emr"""

version := "0.0.1"

scalaVersion := "2.10.4"

jarName in assembly := "example-scalding-emr-task.jar"

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
  case PathList("com","google","protobuf", xs @ _*) => MergeStrategy.discard
  case PathList("com","hadoop", xs @ _*) => MergeStrategy.discard
  case PathList("org","hsqldb", xs @ _*) => MergeStrategy.discard
  case PathList("org","jboss", xs @ _*) => MergeStrategy.discard
  case PathList("org","mortbay", xs @ _*) => MergeStrategy.discard
  case PathList("org","objectweb", xs @ _*) => MergeStrategy.discard
  case PathList("org","objenesis", xs @ _*) => MergeStrategy.discard
  case PathList("org","slf4j", xs @ _*) => MergeStrategy.discard
  case PathList("org","znerd", xs @ _*) => MergeStrategy.discard
  case PathList("thrift", xs @ _*) => MergeStrategy.discard
  case PathList("junit", xs @ _*) => MergeStrategy.discard
  case PathList("org", "apache","jasper", xs @ _*) => MergeStrategy.first
  case PathList("org", "apache","commons", xs @ _*) => MergeStrategy.first
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.first
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.discard
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt"     => MergeStrategy.discard
  case x => old(x)
}
}

// Drop these jars
excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  val excludes = Set(
    "jsp-api-2.1-6.1.14.jar",
    "jsp-2.1-6.1.14.jar",
    "jasper-compiler-5.5.12.jar",
    "minlog-1.2.jar", // Otherwise causes conflicts with Kyro (which bundles it)
    "janino-2.6.1.jar", // Janino includes a broken signature, and is not needed anyway
    "commons-beanutils-core-1.8.0.jar", // Clash with each other and with commons-collections
    "commons-beanutils-1.7.0.jar",      // "
    "hadoop-core-1.0.3.jar", // Provided by Amazon EMR. Delete this line if you're not on EMR
    "hadoop-tools-0.20.2.jar" // "
  )
  cp filter { jar => excludes(jar.data.getName) }
}





libraryDependencies ++= Seq(
  "org.rogach"      %% "scallop"        % "0.9.5",
  "org.apache.avro" % "avro"            % "1.7.6",
  "org.scala-lang"  %% "scala-pickling" % "0.8.0",
  "com.gilt"        %% "gfc-timeuuid"   % "0.0.5"
)


val scaldingVersion = "0.11.1"

libraryDependencies ++= Seq(
	 "com.twitter" %% "scalding-core"     % scaldingVersion,
	 "com.twitter" %% "scalding-commons"  % scaldingVersion,
   "com.twitter" %% "scalding-avro"    % scaldingVersion
)


libraryDependencies ++= Seq(
   "junit"         % "junit"      % "4.8.1" % "test",
   "org.scalatest" %% "scalatest" % "2.1.3" % "test"
)

