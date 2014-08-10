#!/bin/bash
#######################
# Scraper maintenance #
#######################

TMPROOT=/home/ec2-user/scrape/agora-tmp
IMGROOT=/home/ec2-user/scrape/agora-images
KEY=/home/ec2-user/scrape/agora-java/tools/agora_scraper_rsa
RSYNC_DEST=jbalint@enter-agora.com:/home/jbalint/apps/agora_images

######################
# Clean up temp dirs #
######################
cd /home/ec2-user/scrape
for i in $TMPROOT/out $TMPROOT/images $TMPROOT/logs ; do
    NUM=`/bin/ls $i | wc -l`
    if [ $NUM -eq 2 ] ; then
	continue
    fi
    NUM_TO_DEL=$(expr $NUM / 2)
    /bin/ls $i | head -$NUM_TO_DEL | while read j ; do
	echo "Deleting $i/$j"
	rm -rf "$i/$j"
    done
done

###################
# Send new images #
###################
rsync -vaz --remove-source-files -e "ssh -i $KEY" $IMGROOT/ $RSYNC_DEST