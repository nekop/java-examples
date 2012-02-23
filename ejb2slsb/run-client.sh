#!/bin/sh

mvn -Pclient-jboss5 exec:java -Dexec.mainClass=jp.programmers.examples.ejb2.slsb.HelloSLSBClient -Dexec.classpathScope=compile
