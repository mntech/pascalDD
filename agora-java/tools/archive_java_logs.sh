#!/bin/bash

# Archive last months log files (in $ROOT/runlogs right now)

set -e
set -x

M=`date +%m`
M=`expr $M - 1`
Y=`date +%Y`

tar -cvzf runlogs_${Y}_${M}.tar.gz run-${Y}-${M}-*.log
rm run-${Y}-${M}-*.log
