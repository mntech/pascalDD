/*
 * Get the list of scraper run data for the latest run.
 */

select max(run_id) into @curr_run_id from scraper_run;
select count(*) into @host_count from hosts where active = 1;
select count(*) into @done_host_count from scraper_stats where run_id = @curr_run_id;
select sum(scrape_secs + process_secs) into @last_run_time from scraper_stats where run_id = @curr_run_id - 1;
select sum(scrape_secs + process_secs) into @curr_run_time from scraper_stats where run_id = @curr_run_id;

select
  h.title
 ,ss.scrape_secs
 ,ss.process_secs
from
 scraper_stats ss
inner join
 hosts h
on
 ss.host_id = h.id
  and
 ss.run_id = @curr_run_id
;

select
 @last_run_time, @curr_run_time,
 concat(round(@curr_run_time / @last_run_time * 100, 1), '%') as time_done,
 @host_count, @done_host_count,
 concat(round(@done_host_count / @host_count * 100, 1), '%') as hosts_done
;
