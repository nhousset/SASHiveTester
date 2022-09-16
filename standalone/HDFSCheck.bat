@echo off
rem ##################################################################################################################
rem # This script is a wrapper for the HDFSCheck Java program.                                                       #
rem #                                                                                                                #
rem # Requirements:                                                                                                  #
rem #                                                                                                                #
rem # Set the SASJAVA environment variable to <java home>/java, with <java home> being the name of the folder where  #
rem # the java executable that ships with SAS is located. Alternatively, you can set this variable to the location   #
rem # of a java executable whose version is compatible with the SAS software being installed.                        #
rem # Pleae check the install guide/s or contact SAS Tech Support for more information.                              #
rem #                                                                                                                #
rem # Set the CLASSPATH environment variable to point to the folder where the Hadoop client jars are stored, as well #
rem # as to the one where the Hadoop configuration files are located.                                                #
rem #                                                                                                                #
rem # Execution:                                                                                                     #
rem #                                                                                                                #
rem # $SASJAVA HDFSCheck <HDFS server> <HDFS service port number> <HDFS | MAPRFS> <local file name to write to HDFS> #
rem #                                                                                                                #
rem # - <HDFS server>                    : Required. Name of the server where the HDFS service is running            #
rem # - <HDFS service port number>       : Required. HDFS service port number                                        #
rem # - <Filesystem type (HDFS | MAPRFS) : Required. Filesystem type (HDFS | MAPRFS)                                 #
rem # - <Local file name>                : Name of a local file to write to HDFS                                     #
rem # - <Debug flag>                     : Whether to run in debug or silent mode [DEBUG | NODEBUG (default)]        #
rem #                                                                                                                #
rem # Executing the Java program without arguments will display the help.                                            #
rem ##################################################################################################################
@echo on

set SASJAVA="C:\Program Files (x86)\Java\jdk1.7.0_67\bin\java"
set CLASSPATH=.;C:\temp\jars;C:\temp\jars\*;C:\temp\confs;

%SASJAVA% HDFSCheck hadoop1 8020 HDFS HDFSCheck.txt
