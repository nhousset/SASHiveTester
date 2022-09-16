#!/bin/bash


if [ -z ${JAVA_HOME} ]
then
    export JAVA_HOME=/usr/lib/jvm/jre-1.8.0 
fi 

if [ -z ${HADOOP_HOME} ]
then
    export HADOOP_HOME=../hadoop-dist/hadoop-2.7.3
fi 

if [ -z ${HIVE_HOME} ]
then
    export HIVE_HOME=../hive/1.2.1
fi 


export PATH=$PATH:$HADOOP_HOME

echo ${JAVA_HOME}
echo ${HADOOP_HOME}
echo ${HIVE_HOME}

$HIVE_HOME/bin/beeline --incremental=true
