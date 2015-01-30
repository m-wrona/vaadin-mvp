#!/bin/bash

##########################
###Check whether directory exists. If not print error message and exit.
###Params:
### $1 - direcotry path
###Return:
### 1 if directory wasn't found
##########################
function assertDirExists {
	if [ ! -d $1 ]
	then
		echo "Directory $1 not found"
		exit 1
	fi 
}

##########################
###Check whether file exists. If not print error message and exit.
###Params:
### $1 - file path
###Return:
### 1 if file wasn't found
##########################
function assertFileExists {
	if [ ! -f $1 ]
	then
		echo "File $1 not found"
		exit 1
	fi 
}

if [ -z $CATALINA_HOME ]
then
	echo "\$CATALINA_HOME not set"
	exit 1
fi

assertDirExists $CATALINA_HOME
assertFileExists web/target/vaadin-mvp.war

rm -rf $CATALINA_HOME/webapps/vaadin-mvp
rm -rf $CATALINA_HOME/webapps/vaadin-mvp.war

cp web/target/vaadin-mvp.war $CATALINA_HOME/webapps/vaadin-mvp.war
