#!/bin/bash

###############################
# Aggregate lowest level data #
###############################
cd /home/ec2-user/scrape/agora-java
javac -cp lib/mysql-connector-java-5.1.17-bin.jar DbUtil.java
export DBUTIL="java -cp .:lib/mysql-connector-java-5.1.17-bin.jar DbUtil"
UTILARGS="-h agora-scraper-db.chgv9r6xetre.us-east-1.rds.amazonaws.com -u adb -p h10h20xx"
$DBUTIL $UTILARGS file src/agora/sql/fct_run_host_depth_ad_update.sql

