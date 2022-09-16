#!/bin/bash

RED='\033[031m'
GREEN='\033[032m'
YELLOW='\033[033m'
BLUE='\033[034m'
NC='\033[0m' 

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


echo -en "\n\n"
echo -en "${RED}"
echo "   _____          _____   _    _ _             _______        _            "
echo "  / ____|  /\    / ____| | |  | (_)           |__   __|      | |           "
echo " | (___   /  \  | (___   | |__| |___   _____     | | ___  ___| |_ ___ _ __ "
echo "  \___ \ / /\ \  \___ \  |  __  | \ \ / / _ \    | |/ _ \/ __| __/ _ \ '__|"
echo "  ____) / ____ \ ____) | | |  | | |\ V /  __/    | |  __/\__ \ ||  __/ |   "
echo " |_____/_/    \_\_____/  |_|  |_|_| \_/ \___|    |_|\___||___/\__\___|_|   "
echo -en "${NC}\n"
echo -en "${YELLOW}"
echo "  ⚡ https://github.com/nhousset/SASHiveTester/ © Nicolas Housset"
echo -en "${NC}\n" 


echo -en "\n\n"
echo -en "JAVA_HOME :${GREEN}$JAVA_HOME${NC}\n"
echo -en "HADOOP_HOME :${GREEN}$HADOOP_HOME${NC}\n"
echo -en "HIVE_HOME :${GREEN}$HIVE_HOME${NC}\n"
echo -en "\n\n"
echo -en "BEELINE CMD :${GREEN} $HIVE_HOME/bin/beeline --incremental=true ${NC}\n"
echo -en "\n\n"

                                                                       
                                                                           
