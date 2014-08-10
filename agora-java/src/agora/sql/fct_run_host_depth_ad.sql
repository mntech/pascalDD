/*
 * Lowest level aggregate table
 *
 * (*CREATION*) - see fct_run_host_depth_ad_update.sql for incremental updates
 *
 * Grouped fields:
 *   Run ID
 *   Host ID
 *   Depth
 *   Image File ID
 */

set session transaction isolation level read uncommitted;

drop table if exists tmp_fct_run_host_depth_ad;
CREATE TABLE `tmp_fct_run_host_depth_ad` (
  `run_id` int(10) unsigned DEFAULT NULL,
  `hostId` char(36) DEFAULT NULL,
  `depth` int(11) NOT NULL DEFAULT '0',
  `imageFileId` char(36) DEFAULT NULL,
  `ad_count` bigint(21) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

insert into tmp_fct_run_host_depth_ad
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
group by 1,2,3,4
;

alter table tmp_fct_run_host_depth_ad add primary key (run_id, hostId, depth, imageFileId);

drop table if exists fct_run_host_depth_ad;
alter table tmp_fct_run_host_depth_ad rename to fct_run_host_depth_ad;
