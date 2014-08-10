#!/bin/sh

set -e
set -x
# data maintenance operations
# things that arent done during scrape time

export PATH=/bin:/usr/bin
export DBUTIL="java -cp .:lib/mysql-connector-java-5.1.17-bin.jar DbUtil"

javac -cp lib/mysql-connector-java-5.1.17-bin.jar DbUtil.java

# update low level aggregate - moved to scraper server
#$DBUTIL file src/agora/sql/fct_run_host_depth_ad_update.sql
# pull from scraper
$DBUTIL file src/agora/sql/pull_scraper_fct_run_host_depth_ad_update.sql
$DBUTIL file src/agora/sql/pull_scraper_imageFile_records.sql

# update flash sizes based on HTML snippets
$DBUTIL updateFlashSizes

# update aspect ratios
$DBUTIL updateAspectRatio

# update width and height (only used in top advertisers aggregate table as of 2/28/2012)
$DBUTIL updateWidthAndHeight

# flow data to DE
$DBUTIL file flow_data_to_de.sql

# update fact tables
$DBUTIL file src/agora/sql/fct_ad_month_site_placements_update.sql
$DBUTIL file src/agora/sql/fct_advertiser_host_month_ad_smash_update.sql

# update imageFile.derived_lastDate
$DBUTIL file src/agora/sql/derived_lastDate_update.sql
