#!/bin/bash
########################################################
# Check existence of ad image files                    #
#                                                      #
# finds any imageFile records missing an ad image file #
########################################################

NOTICES=
IMGDIR=/home/jbalint/apps/agora_images
NOTICES=`/opt/mysql/server-5.5/bin/mysql -B -N -u agora_scrape -p6Ra2RuTr -e 'select filename from imageFile where createdate < date_sub(now(), interval 1 day)' agora_scrape | while read i ; do
	if [ ! -e $IMGDIR/$i ] ; then
		echo "FILE MISSING: " $i
	fi
done`

if [ "$NOTICES" != "" ] ; then
    echo "Sending notification email: $NOTICES"
    echo -e "$NOTICES" | mail -s "Agora NOTIFICATION: Image Files Missing" jbalint@gmail.com mark@kadekraus.com
fi
