#!/bin/bash

### BEGIN INIT INFO
# Provides: tomcat
# Required-Start:  $network
# Required-Stop:   $network
# Default-Start:   2 3 4 5
# Default-Stop:    0 1 6
# Short-Description: Start/Stop Tomcat Server
### END INIT INFO

echo #Attempting to start Tomcat"

case "$1" in
  start)
    echo "Starting Tomcat"
    sh /opt/tomcat/bin/startup.sh
    echo "Tomcat is alive"
    ;;
  stop)
    echo "Stopping Tomcat"
    sh /opt/tomcat/bin/shutdown.sh
    echo "Tomcat is dead"
    ;;
  *)
    echo "Usage: /etc/init.d/tomcat {start|stop}"
    exit 1
    ;;
esac

exit 0

