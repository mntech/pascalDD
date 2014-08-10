/* derived_lastDate update - takes about five seconds for 20k imageFile rows */
drop table if exists image_lastDate;
create temporary table image_lastDate as select image_num,max(month_last) as last_date from fct_ad_month_site_placements group by 1;
create index x_max on image_lastDate (image_num, last_date);

update imageFile set derived_lastDate = (select last_date from image_lastDate where image_num = imageFile.image_num);
