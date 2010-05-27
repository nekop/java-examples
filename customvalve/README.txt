* What is this project

Includes useful JBoss Web custom valve components and works with JBoss AS 4.2 series. Currently we only have ConfigureSessionCookieValve which allows you to modify attirbutes on JSESSIONID cookies.

* Building

Edit the build.properties and run ant command.

* How to use

1. Copy the target/customvalve.jar into $JBOSS_HOME/server/$JBOSS_SERVER_CONFIG/lib/ or $JBOSS_HOME/server/$JBOSS_SERVER_CONFIG/deploy/jboss-web.deployer/ directory. You can use "ant deploy" command which copys the jar file into lib dir.

2. Write context.xml file and put it as WEB-INF/context.xml in your WAR archive. This project includes example context.xml file.
