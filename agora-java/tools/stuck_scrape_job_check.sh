#!/bin/bash

# dont quote this when using it in command, diff emails need to be separate command line args
MAILTO="jbalint@gmail.com mark@kadekraus.com

# highest runtime we tolerate before sending a notice
MAX_RUNTIME=8

STUCK_JOBS=`/opt/mysql/server-5.5/bin/mysql -u agora_scrape -p6Ra2RuTr -e "select * from (select *, hour(timediff(now(), start_time)) as run_time from scraper_jobs where end_time is null and start_time is not null) as x where x.run_time > $MAX_RUNTIME \G" agora_scrape`

if [ "$STUCK_JOBS" != "" ] ; then
    echo "Sending notification email: $MAILTO"
    echo -e "Please check Agora scraper for stuck jobs (run time is over $MAX_RUNTIME hours):\n\n$STUCK_JOBS" | mail -s "Agora NOTIFICATION: Scraper Jobs Long Runtime" $MAILTO
fi
