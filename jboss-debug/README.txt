* What is this project

This project aims to be a collection of classes which are useful for debugging JBoss Application Server.

- aop/MethodCallLoggingInterceptor

This is a JBoss AOP Interceptor implementation which generates start/end log on every call.

- tx/*

Mock XAResource implementation which works with JBoss Transactions. It stores prepared transaction information into files under data dir, so you can use this for crash recovery tests.

- log4j/DumpStackTraceFilter

Dump a stacktrace when log4j received specified log message.

* Building

Edit the build.properties and run ant command.

* How to use

Copy the target/jboss-debug.jar into $JBOSS_HOME/server/$JBOSS_SERVER_CONFIG/lib/ directory. You can use "ant deploy" command which copys the jar file into lib dir.

- aop/MethodCallLoggingInterceptor

For EJB3 you will just modify deploy/ejb3-interceptors-aop.xml to enable this interceptor. For the other classes, you need to enable JBoss AOP load-time instrumentation and deploy -aop.xml file. Example aop definition file is available at etc/example-aop.xml.

- log4j/DumpStackTraceFilter

Example is available at etc/example-log4j.xml. Copy it into your log4j appender definition.
