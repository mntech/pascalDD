#!/bin/bash
###################################################
# Tomcat check script for Supranet (Agora, Oasis) #
#                                                 #
# server crashes sometimes...                     #
###################################################

X=`curl "http://enter-agora.com/A3lifesci/demoRecentImages.m" | grep '<title>Essentia: AGORA</title>' | wc -l`

if [ $X -ne 1 ] ; then
	echo "Tomcat server is down, trying to restart"
	echo "Tomcat server is down, trying to restart" | mail -s "Supranet Server Down" jbalint@gmail.com mark@kadekraus.com
	sudo service tomcat7 restart
fi
