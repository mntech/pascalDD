#!/bin/sh
# Basic script to check the main scraper log for errors (timeouts, etc)

MAILTO=jbalint@gmail.com

LOGFILE=`/bin/ls -tr runlogs/run-*.log | tail -1`
echo "Log file is $LOGFILE"

ERRORS=`grep -n -A 0 ERROR $LOGFILE | grep -v 'content type is not an image:' | grep -v 'invalid curl result:'`

if [ "$ERRORS" != "" ] ; then
    echo "Mailing"
    echo "$ERRORS" | mail -s "Scraper Errors in $LOGFILE" $MAILTO
fi
