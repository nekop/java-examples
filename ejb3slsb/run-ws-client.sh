#!/bin/sh

mvn -X exec:java -Dexec.mainClass=jp.programmers.examples.ejb3.slsb.ws.HelloWSClient -Dexec.classpathScope=compile -Dlog4j.configuration=client-log4j.xml 
