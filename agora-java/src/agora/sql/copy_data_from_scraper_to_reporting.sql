/*
 * Copy data from scraper server to reporting server
 *
 * uses federated tables on the reporting server that point
 * to the scraper server
 */


/* copy new imageFile records */
insert into agora_scrape.imageFile
select * from agora_remote.imageFile
where image_num > (select max(image_num) from agora_scrape.imageFile);

/* copy new scraper_run records */
insert into agora_scrape.scraper_run
select * from agora_remote.scraper_run
where run_id > (select max(run_id) from agora_scrape.scraper_run);

/* copy new low-level aggregate data */
insert into agora_scrape.fct_run_host_depth_ad
select * from agora_remote.fct_run_host_depth_ad
where run_id > (select max(run_id) from agora_scrape.fct_run_host_depth_ad);
