#!/bin/sh
MAINCLASS="com.moredian.skynet.SkynetApplication"
JAVA_OPTS="-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -server -Xms512m -Xmx1024m -Xmn256m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs/heap_dump.bin -XX:PermSize=64m -XX:MaxPermSize=128m -XX:+UseParallelOldGC -XX:MaxTenuringThreshold=13 -Xdebug -Xrunjdwp:transport=dt_socket,address=12003,server=y,suspend=n"
SERVERHOME="$(cd ${SERVERBINDIR}/..; pwd)"
SERVERCFGDIR="$SERVERHOME/config"
SERVERLOGDIR="$SERVERHOME/logs"
SERVERCOMAND="${SERVERBINDIR}/server.sh"
if [ ! -d "$SERVERLOGDIR" ];then
	mkdir $SERVERLOGDIR
fi

SERVERLOGOUT="$SERVERLOGDIR/server.out"
SERVERPIDFILE="$SERVERLOGDIR/server.pid"

if [ -d "${SERVERHOME}/lib" ];then
	SERVERLIBDIR="$SERVERHOME/lib"
elif [ -d "${SERVERHOME}/webapp/WEB-INF/lib" ];then
	SERVERLIBDIR="${SERVERHOME}/webapp/WEB-INF/lib"
fi

if [ "$JAVA_HOME" != "" ]; then
  JAVA="$JAVA_HOME/bin/java"
else
  JAVA=java
fi


CLASSPATH=$SERVERCFGDIR

for i in `ls $SERVERLIBDIR`
do
CLASSPATH=$CLASSPATH:$SERVERLIBDIR/$i
done
cd $SERVERHOME


