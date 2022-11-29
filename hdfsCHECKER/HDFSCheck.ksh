#!/bin/ksh

##################################################################################################################
# This script is a wrapper for the HDFSCheck Java program.                                                       #
#                                                                                                                #
# Requirements:                                                                                                  #
#                                                                                                                #
#                                                                                                                #
# Execution:                                                                                                     #
#                                                                                                                #
# $SASJAVA HDFSCheck <HDFS server> <HDFS service port number> <HDFS | MAPRFS> <local file name to write to HDFS> #
#                                                                                                                #
# - <HDFS server>                    : Required. Name of the server where the HDFS service is running            #
# - <HDFS service port number>       : Required. HDFS service port number                                        #
# - <Filesystem type (HDFS | MAPRFS) : Required. Filesystem type (HDFS | MAPRFS)                                 #
# - <Local file name>                : Name of a local file to write to HDFS                                     #
# - <Debug flag>                     : Whether to run in debug or silent mode [DEBUG | NODEBUG (default)]        #
#                                                                                                                #
# Executing the Java program without arguments will display the help.                                            #
##################################################################################################################

export SASJAVA=/usr/bin/java
export CLASSPATH=.:/opt/hadoop/Hadoop_DEV/jars/*:/opt/hadoop/Hadoop_DEV/sitexmls:

$SASJAVA HDFSCheck $1 $2 HDFS HDFSCheck.txt
