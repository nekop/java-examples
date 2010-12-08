#!/bin/sh

mvn -Pclient-eap510 exec:java -Dexec.mainClass=jp.programmers.examples.ejb3.slsb.HelloSLSBClient -Dexec.classpathScope=compile
