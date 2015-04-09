#!/bin/sh

mvn -Pclient-jboss5 exec:java -Dexec.mainClass=jp.programmers.examples.ejb3.sfsb.HelloSFSBClient -Dexec.classpathScope=compile -Dlog4j.configuration=client-log4j.xml
