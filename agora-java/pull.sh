#!/bin/bash

# backup for production agora server

git pull
sudo /etc/init.d/tomcat7 stop
rm -r /var/lib/tomcat7/webapps/agoradd/*
cp -r /home/pascalDD/agora-java/build/. /var/lib/tomcat7/webapps/agoradd/
sudo /etc/init.d/tomcat7 start
tail -f /var/lib/tomcat7/logs/catalina.out