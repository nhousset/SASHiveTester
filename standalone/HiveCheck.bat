@echo off
rem ################################################################################################################################
rem # This script is a wrapper for the HiveCheck Java program.                                                                     #
rem #                                                                                                                              #
rem # Requirements:                                                                                                                #
rem #                                                                                                                              #
rem # Set the SASJAVA environment variable to <java home>/java, with <java home> being the name of the folder where the java       #
rem # executable that ships with SAS is located. Alternatively, you can set this variable to the location of a java executable     #
rem # whose version is compatible with the SAS software being installed.                                                           #
rem # Pleae check the install guide/s or contact SAS Tech Support for more information.                                            #
rem #                                                                                                                              #
rem # Set the CLASSPATH environment variable to point to the folder where the Hadoop client jars are stored, as well               #
rem # as to the one where the Hadoop configuration files are located.                                                              #
rem # Execution:                                                                                                                   #
rem #                                                                                                                              #
rem # $SASJAVA HIVECheck <Authentication type> <Hive server> <Hive service port number> [<Kerberos Principal> | <user> <password>] #
rem #                                                                                                                              #
rem # - <Authentication type>      : Required. SIMPLE, LDAP, or KERBEROS                                                           #
rem # - <Hive server>              : Required. Name of the server where the Hive service is running                                #
rem # - <Hive service port number> : Required. Hive service port number                                                            #
rem # - <Kerberos Principal>       : With KERBEROS authentication, the HiveServer2 principal (i.e. hive/_HOST@MY.DOMAIN.COM)       #
rem #                                Check the hive.server2.authentication.kerberos.principal in your hive-site.xml file to obtain #
rem #                                it.                                                                                           #
rem # - <user> <password>          : With LDAP authentication, a valid set of credentials to connect to the cluster                #
rem # - <Debug flag>               : Whether to run in debug or silent mode [DEBUG | NODEBUG (default)]                            #
rem #                                                                                                                              #
rem # Executing the Java program without arguments will display the help.                                                          #
rem ################################################################################################################################
@echo on

set SASJAVA="C:\Program Files (x86)\Java\jdk1.7.0_67\bin\java"
set CLASSPATH=.;C:\temp\jars;C:\temp\jars\*;C:\temp\confs;

%SASJAVA% HiveCheck KERBEROS hadoop1 10000 hive/_HOST@PSD.SAS.COM
