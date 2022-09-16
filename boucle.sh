logfile=/tmp/$$.log
exec > $logfile 2>&1

URL=$1
USER=$2
PASS=$3

if [ ! "$1" ]
then
 echo $0 "<URL JDBC> <USER> <PASSWORD>"
  exit 1;
fi
for i in 1 2 3 4 5 6 7 8 9 10
do
echo "RUN ========================= "$i
time $HIVE_HOME/bin/beeline -u $URL --incremental=true -n $USER -p $PASS &<<EOF
show databases;
use in_usage_nacsel_expo_dtm;
show tables;
!q
EOF

echo "FIN RUN =============================== "$i
done
