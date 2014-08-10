#!/bin/bash

# backup for REPORTING agora server (supranet)

set -e
set -x

export PATH=/usr/bin:$PATH
KEY=/home/jbalint/.ssh/agora_backup

DEST=agora_backup@ps78295.dreamhost.com
SCRAPER=jbalint@ps63603.dreamhost.com

MYSQL_BKUP=/var/tmp/agora_scrape_`date +\%F`.sql.bz2

# we only backup tables we need (mainly to conserve bandwidth on this host because it costs more)
/opt/mysql/server-5.5/bin/mysqldump -u root -pCrubu9eduv6heg agora_scrape authorities company data_entry ftp_images hosts imageFile keyword_search_log user_domain_set users welcome_text | /bin/bzip2 > $MYSQL_BKUP
rsync -va -e "ssh -i $KEY" $MYSQL_BKUP $DEST:/home/agora_backup/reporting/database
rm $MYSQL_BKUP

# backup configs
rsync -va -e "ssh -i $KEY" /etc/tomcat7/context.xml                  $DEST:/home/agora_backup/reporting/config
rsync -va -e "ssh -i $KEY" /etc/tomcat7/server.xml                   $DEST:/home/agora_backup/reporting/config
rsync -va -e "ssh -i $KEY" /etc/my.cnf                               $DEST:/home/agora_backup/reporting/config
rsync -va -e "ssh -i $KEY" /etc/apache2/sites-available/default      $DEST:/home/agora_backup/reporting/config
rsync -va -e "ssh -i $KEY" /etc/libapache2-mod-jk/workers.properties $DEST:/home/agora_backup/reporting/config

# copy new images from scraper
rsync -va -e "ssh -i $KEY" $SCRAPER:/home/jbalint/agora/images/ /var/lib/tomcat7/essentia/agora_images

