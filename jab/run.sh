#!/bin/sh

# $ mvn package && sh run.sh -c 400 -n 100000 http://localhost:8080/

mvn exec:java -Dexec.args="$*"
