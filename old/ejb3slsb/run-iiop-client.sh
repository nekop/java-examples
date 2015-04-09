#!/bin/sh

JBOSS_HOME=/home/nekop/jboss512

CLASSPATH="target/classes"
CLASSPATH="$CLASSPATH:$JBOSS_HOME/client/jboss-javaee.jar"

JAVA_OPTS="$JAVA_OPTS -Djava.security.manager -Djava.security.policy=iiop.policy"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.CORBA.ORBDebug=transport,giop,naming,invocationTiming"
#JAVA_OPTS="$JAVA_OPTS -Dsun.rmi.log.debug=true -Dsun.rmi.loader.logLevel=VERBOSE -Dsun.rmi.transport.logLevel=VERBOSE -Dsun.rmi.transport.tcp.logLevel=VERBOSE"

java -classpath $CLASSPATH $JAVA_OPTS jp.programmers.examples.ejb3.slsb.iiop.HelloIIOPClient
