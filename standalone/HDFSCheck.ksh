#!/bin/ksh

##################################################################################################################
# This script is a wrapper for the HDFSCheck Java program.                                                       #
#                                                                                                                #
# Requirements:                                                                                                  #
#                                                                                                                #
# Set the SASJAVA environment variable to <java home>/java, with <java home> being the name of the folder where  #
# the java executable that ships with SAS is located. Alternatively, you can set this variable to the location   #
# of a java executable whose version is compatible with the SAS software being installed.                        #
# Pleae check the install guide/s or contact SAS Tech Support for more information.                              #
#                                                                                                                #
# Set the CLASSPATH environment variable to point to the folder where the Hadoop client jars are stored, as well #
# as to the one where the Hadoop configuration files are located.                                                #
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
export CLASSPATH=.:/tmp/jars/*:/tmp/confs/:

$SASJAVA HDFSCheck hadoop1 8020 HDFS HDFSCheck.txt 
