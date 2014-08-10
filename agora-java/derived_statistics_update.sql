/* MAIN aggregate table - ad, month, site -> placements */
drop table if exists tmp_BUILD_fct_ad_month_site_placements;
create table tmp_BUILD_fct_ad_month_site_placements engine=myisam as
select
	f.id,
	f.image_num,
	concat(date_format(pulltime, '%Y-%m'), '-01') as month,
	h.id as hostId,
	h.path,
	min(cast(p.pulltime as date)) as month_first,
	max(cast(p.pulltime as date)) as month_last,
	group_concat(distinct p.depth order by p.depth separator ', ') as depths,
	count(i.id) as ad_count
from
	imageFile f
inner join
      image i on i.imageFileId = f.id
inner join
      pulls p on i.pullId = p.id
inner join
      hosts h on p.hostId = h.id
group by 1,2,3,4,5
;
alter table tmp_BUILD_fct_ad_month_site_placements add unique key (id, month, path);
alter table tmp_BUILD_fct_ad_month_site_placements add unique key (image_num, month, path);
;

drop table if exists fct_ad_month_site_placements;
alter table tmp_BUILD_fct_ad_month_site_placements rename to fct_ad_month_site_placements;

/* table with URLs for placement detail metric */
drop table if exists fct_image_month_urls;
create table fct_image_month_urls engine=myisam as
select i.imageFileId,
concat(date_format(p.pulltime, '%Y-%m'), '-01') as month,
p.path
from image i
inner join pulls p on i.pullId = p.id
group by 1, 2, 3;

