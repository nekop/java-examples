#!/bin/sh

JBOSS_HOME=~/eap6

mvn clean package
cd target
mkdir -p empty
cd empty
jar cf ee6-ejb-interfaces-web.war .
mv ee6-ejb-interfaces-web.war ..
cd ..
rmdir empty
jar cf ee6-ejb-interfaces.ear ee6-ejb-interfaces-web.war ee6-ejb-interfaces.jar
rm ee6-ejb-interfaces-web.war
cd ..

cp target/ee6-ejb-interfaces.ear $JBOSS_HOME/standalone/deployments/
