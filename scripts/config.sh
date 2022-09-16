#!/bin/bash


if [ -z ${JAVA_HOME} ]
then
    JAVA_HOME=/usr/lib/jvm/jre-1.8.0 
fi 

if [ -z ${HADOOP_HOME} ]
then
    HADOOP_HOME=../hadoop-dist/hadoop-2.7.3
fi 

if [ -z ${HIVE_HOME} ]
then
    HIVE_HOME=../hive/1.2.1
fi 


export ${JAVA_HOME}
export HADOOP_HOME=../hadoop-dist/hadoop-2.7.3
export HIVE_HOME=../hive/1.2.1
export PATH=$PATH:$HADOOP_HOME

echo ${JAVA_HOME}
echo ${HADOOP_HOME}
echo ${HIVE_HOME}

$HIVE_HOME/bin/beeline --incremental=true
