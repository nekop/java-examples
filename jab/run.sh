#!/bin/sh

ulimit -u 8192

# $ mvn package && sh run.sh -w -c 400 -n 100000 http://localhost:8080/

mvn exec:java -Dexec.mainClass=jp.programmers.jab.JAB -Dexec.args="$*"
