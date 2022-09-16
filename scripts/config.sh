#!/bin/bash
function validate_java_home {
    if [ -z ${JAVA_HOME} ]
    then
        # do something if the file doesn't provide ${JAVA_HOME}
    else 
        if [ ! -e ${JAVA_HOME} ]
        then
            # do something if the file provides a non existent ${JAVA_HOME}
        fi
    fi
}

if [ ! -e ${YOUR_FILE_NAME_CONTAINING_JAVA_HOME} ]
then
     JAVA_HOME_CANDIDATES=$(find /usr -name 'jdk1.*')
     echo "Found the following candidates for JAVA_HOME, reply with the one you want then press return"
     echo ""
     echo $JAVA_HOME_CANDIDATES
     read USER_SUBMITTED_JAVA_HOME
     echo "You chose $USER_SUBMITTED_JAVA_HOME"
     JAVA_HOME=${USER_SUBMITTED_JAVA_HOME}
else
    . ${YOUR_FILE_NAME_CONTAINING_JAVA_HOME}
fi 
validate_java_home
export ${JAVA_HOME}
echo $JAVA_HOME

exit;

export HADOOP_HOME=../hadoop-dist/hadoop-2.7.3
export HIVE_HOME=../hive/1.2.1
export PATH=$PATH:$HADOOP_HOME
sh -x $HIVE_HOME/bin/beeline --incremental=true
