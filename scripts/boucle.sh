logfile=/tmp/$$.log
exec > $logfile 2>&1

source ./config.sh

URL=$1
USER=$2
PASS=$3

if [ ! "$1" ]
then
 echo $0 "<URL JDBC> <USER> <PASSWORD>"
  exit 1;
fi
for i in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30
do
echo "RUN ========================= "$i
time $HIVE_HOME/bin/beeline -u $URL --incremental=true -n $USER -p $PASS &<<EOF
show databases;
show tables;
!q
EOF

echo "FIN RUN =============================== "$i
done
