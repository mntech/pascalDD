/*
Get the latest aggregate data from the scraper db!
*/

insert into fct_run_host_depth_ad
select * from agora_remote.fct_run_host_depth_ad
where run_id > (select max(run_id) from fct_run_host_depth_ad)
;