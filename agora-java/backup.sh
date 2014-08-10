#!/bin/bash

# backup for production agora server

export PATH=/usr/bin:$PATH
KEY=/home/jbalint/.ssh/agora_backup

DEST=agora_backup@ps78295

rsync -va -e "ssh -i $KEY" /home/jbalint/agora/images/* $DEST:/home/agora_backup/images
rsync -va -e "ssh -i $KEY" /home/jbalint/mysql/backup/* $DEST:/home/agora_backup/database
rsync -va -e "ssh -i $KEY" /home/htmlgit/*.git $DEST:/home/agora_backup/code

rsync -va -e "ssh -i $KEY" /home/jbalint/apache-tomcat-6.0.33/conf/server.xml $DEST:/home/agora_backup/config
rsync -va -e "ssh -i $KEY" /home/jbalint/mysql/my.cnf $DEST:/home/agora_backup/config
rsync -va -e "ssh -i $KEY" /etc/apache2/sites-enabled/000-default $DEST:/home/agora_backup/config
rsync -va -e "ssh -i $KEY" /etc/apache2/workers.properties $DEST:/home/agora_backup/config

