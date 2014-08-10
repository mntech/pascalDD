/* current process, takes a trivial amount of time */

drop table if exists tmp_fct_advertiser_host_month_ad_smash;
create table tmp_fct_advertiser_host_month_ad_smash engine=myisam as
select
   de.company
  ,pl.hostId
  ,pl.month
  ,pl.id as imageFileId
  ,sum(pl.ad_count) as ad_count
  ,sum(ifile.width * ifile.height * pl.ad_count) as ad_area
from
  fct_ad_month_site_placements pl
inner join
  data_entry de
on
  pl.image_num = de.file_number
    and
  de.de_status = 1
inner join
  imageFile ifile
on
  ifile.id = pl.id
group by 1, 2, 3, 4
;

alter table tmp_fct_advertiser_host_month_ad_smash add unique key (company, hostId, month, imageFileId);

alter table tmp_fct_advertiser_host_month_ad_smash add key (hostId, month);

alter table tmp_fct_advertiser_host_month_ad_smash add key (month);

drop table if exists fct_advertiser_host_month_ad_smash;
alter table tmp_fct_advertiser_host_month_ad_smash rename to fct_advertiser_host_month_ad_smash;

