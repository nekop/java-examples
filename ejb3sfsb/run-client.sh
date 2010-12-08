#!/bin/sh

mvn -Pclient-eap510 exec:java -Dexec.mainClass=jp.programmers.examples.ejb3.sfsb.HelloSFSBClient -Dexec.classpathScope=compile
