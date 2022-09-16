export HADOOP_HOME=../hadoop-dist/hadoop-2.7.3
export HIVE_HOME=../hive/1.2.1
export PATH=$PATH:$HADOOP_HOME
sh -x $HIVE_HOME/bin/beeline --incremental=true
