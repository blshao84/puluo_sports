
name := "lift_server"

libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"

libraryDependencies ++= {
  val liftVersion = "2.6-M2"
  Seq(
    "net.liftmodules" %% "fobo_2.6"    % "1.1"       % "compile" withSources(),
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile" withSources(),
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile" withSources(),
    "net.liftweb"       %% "lift-wizard"        % liftVersion        % "compile" withSources(),
    "net.liftweb"       %% "lift-util" %  liftVersion % "compile" withSources(),
    "net.liftweb"       %% "lift-actor" %  liftVersion % "compile" withSources(),
    "net.liftweb"       %% "lift-common" %  liftVersion % "compile" withSources(),
    "net.liftweb"       %% "lift-db" %  liftVersion % "compile" withSources(),
    "net.liftweb"       %% "lift-proto" %  liftVersion % "compile" withSources(),
    "net.liftweb"       %% "lift-testkit"        % liftVersion  % "compile" withSources(),
    "org.apache.httpcomponents" % "httpmime" % "4.2.2" % "compile" withSources(),
    "org.eclipse.jetty" % "jetty-webapp"   %   "9.1.0.v20131115" % "test" withSources(),
    "org.eclipse.jetty" % "jetty-plus" % "9.1.0.v20131115" % "test" withSources(),
    "javax.servlet" % "javax.servlet-api" % "3.1.0" % "test" withSources(),
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
