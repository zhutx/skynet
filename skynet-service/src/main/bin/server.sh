#!/bin/sh

SERVERBIN="${BASH_SOURCE-$0}"
SERVERBIN="$(dirname "${SERVERBIN}")"
SERVERBINDIR="$(cd "${SERVERBIN}"; pwd)"

. "$SERVERBINDIR/env.sh"

case $1 in
start)    echo "Starting server ... "
    if [ -f "$SERVERPIDFILE" ]; then
		 kill -0 `cat "$SERVERPIDFILE"` > /dev/null 2>&1
        if [ $? -eq 0 ]; then
          echo server already running as process `cat "$SERVERPIDFILE"`. 
          exit 0
        fi
    fi
    echo "$JAVA_HOME"
    echo "$JAVA"
    nohup "$JAVA" -cp "$CLASSPATH" $JAVA_OPTS $MAINCLASS > "$SERVERLOGOUT" 2>&1 < /dev/null & 
    if [ $? -eq 0 ]
    then
      case "$OSTYPE" in
      *solaris*)
        /bin/echo "${!}\\c" > "$SERVERPIDFILE"
        ;;
      *)
        /bin/echo -n $! > "$SERVERPIDFILE"
        ;;
      esac
      if [ $? -eq 0 ];
      then
        sleep 1
        echo STARTED
      else
        echo FAILED TO WRITE PID
        exit 1
      fi
    else
      echo SERVER DID NOT START
      exit 1
    fi

	;;
stop)
    echo "Stopping server ... "
    if [ ! -f "$SERVERPIDFILE" ]
    then
      echo "no server to stop (could not find file $SERVERPIDFILE)"
    else
	  kill -15 $(cat "$SERVERPIDFILE")
	  KILL_SLEEP_INTERVAL=5
	  while [ $KILL_SLEEP_INTERVAL -ge 0 ]; do
		kill -0 `cat "$SERVERPIDFILE"` > /dev/null 2>&1
		if [ $? -eq 0 ]; then
			sleep 1
			KILL_SLEEP_INTERVAL=`expr $KILL_SLEEP_INTERVAL - 1 `
	    else
			break
	    fi
	   done
	   if [ $KILL_SLEEP_INTERVAL -lt 0 ]; then
         kill -9 `cat "$SERVERPIDFILE"` > /dev/null 2>&1
       fi
      rm "$SERVERPIDFILE"
      echo "STOPPED"
    fi
    exit 0

	;;
restart)
	shift
    $SERVERCOMAND stop ${@}
    $SERVERCOMAND start ${@}
	;;
del)
	shift
    rm -rf $SERVERLIBDIR
	rm -rf $SERVERCFGDIR
	rm -rf $SERVERBINDIR
	;;
	
print-cmd)
	echo "\"$JAVA\" -cp \"$CLASSPATH\" $JAVA_OPTS $MAINCLASS > \"$SERVERLOGOUT\" 2>&1 < /dev/null"
	;;
*)
	echo "Usage: $0 {start|stop|restart|print-cmd|del}"
esac
