#!/bin/sh
# Make sure the DE queue is not empty (to be run right after DE queue is updated)
# NOTE: the password is specific to the Supranet/web server

# dont quote this when using it in command, diff emails need to be separate command line args
MAILTO="jbalint@gmail.com mark@kadekraus.com"
QUEUE_SIZE=`/opt/mysql/server-5.5/bin/mysql -B -N -u agora_scrape -p6Ra2RuTr -e 'select count(f.ftp_image_id) from ftp_images f left join data_entry d on f.ftp_image_id = d.file_number where d.file_number is null' agora_scrape`

if [ $QUEUE_SIZE = 0 ] ; then
    echo "The Agora DE queue is empty and it probably shouldn't be. Please check it.\n\nSent at `date`" | mail -s "Agora DE Queue - Empty at `date`" $MAILTO
fi

