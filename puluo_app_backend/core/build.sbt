
name := "core"

version := "0.1"

libraryDependencies ++= {
  val liftVersion = "2.6-M2"
  Seq(
    "org.apache.httpcomponents" % "httpmime" % "4.2.2" % "compile" withSources(),
    "dom4j" % "dom4j"        % "1.6.1"  % "compile" withSources(),
    "postgresql" % "postgresql" % "9.0-801.jdbc4" % "compile" withSources(),
    "ch.qos.logback" % "logback-classic" % "0.9.28",
    "org.specs2"        %% "specs2"             % "2.3.7"           % "test" withSources(),
    "com.h2database"    % "h2"                  % "1.3.167",
    //"junit"             % "junit"               % "4.5"              % "test->default",
    "org.scalatest" %% "scalatest" % "2.0" % "test" exclude("org.scala-lang", "scalap") withSources(),
    "org.apache.httpcomponents" % "httpclient" % "4.2.3" % "compile" withSources(),
    "commons-httpclient" % "commons-httpclient" % "3.1" withSources(),
    "com.belerweb" % "pinyin4j" % "2.5.0" withSources(),
    "net.sf.json-lib" % "json-lib" % "2.4" classifier "jdk15",
    "jaxen" % "jaxen" % "1.1.6" withSources()
        )
}
