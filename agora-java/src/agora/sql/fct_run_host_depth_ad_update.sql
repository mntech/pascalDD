/*
 * Lowest level aggregate table
 *
 * (*UPDATE*) for a single run id
 * run immediately following scraper run
 */

set session transaction isolation level read uncommitted;

insert into fct_run_host_depth_ad
select
  p.run_id,
  p.hostId,
  p.depth,
  i.imageFileId,
  count(i.id) as ad_count
from
  image i
inner join
  pulls p
on
  i.pullId = p.id
    and
  p.run_id between
  	   (select 1+max(run_id) from fct_run_host_depth_ad)
	   and
	   coalesce((select min(run_id)-1 from scraper_jobs where end_time is null),
	   	        (select max(run_id) from scraper_jobs))
group by 1,2,3,4;