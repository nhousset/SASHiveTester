#!/bin/ksh

################################################################################################################################
# This script is a wrapper for the HiveCheck Java program.                                                                     #
#                                                                                                                              #
# Requirements:                                                                                                                #
#                                                                                                                              #
# Set the SASJAVA environment variable to <java home>/java, with <java home> being the name of the folder where the java       #
# executable that ships with SAS is located. Alternatively, you can set this variable to the location of a java executable     #
# whose version is compatible with the SAS software being installed.                                                           #
# Pleae check the install guide/s or contact SAS Tech Support for more information.                                            #
#                                                                                                                              #
# Set the CLASSPATH environment variable to point to the folder where the Hadoop client jars are stored, as well               #
# as to the one where the Hadoop configuration files are located.                                                              #
# Execution:                                                                                                                   #
#                                                                                                                              #
# $SASJAVA HIVECheck <Authentication type> <Hive server> <Hive service port number> [<Kerberos Principal> | <user> <password>] #
#                                                                                                                              #
# - <Authentication type>      : Required. SIMPLE, LDAP, or KERBEROS                                                           #
# - <Hive server>              : Required. Name of the server where the Hive service is running                                #
# - <Hive service port number> : Required. Hive service port number                                                            #
# - <Kerberos Principal>       : With KERBEROS authentication, the HiveServer2 principal (i.e. hive/_HOST@MY.DOMAIN.COM)       #
#                                Check the hive.server2.authentication.kerberos.principal in your hive-site.xml file to obtain #
#                                it.                                                                                           #
# - <user> <password>          : With LDAP authentication, a valid set of credentials to connect to the cluster                #
# - <Debug flag>               : Whether to run in debug or silent mode [DEBUG | NODEBUG (default)]                            #
#                                                                                                                              #
# Executing the Java program without arguments will display the help.                                                          #
################################################################################################################################

export SASJAVA=/usr/bin/java
export CLASSPATH=.:/tmp/jars/*:/tmp/confs/:

$SASJAVA HiveCheck KERBEROS hadoop1 10000 hive/_HOST@PSD.SAS.COM 
