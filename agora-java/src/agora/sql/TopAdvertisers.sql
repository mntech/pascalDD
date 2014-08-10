/* Some information

   S_1 = sites - how many different hosts/domains this advertiser was seen on
   M = messages - number of ad images
   A = ad area (divided by a million)
   S_2 = relative OCCURRENCES.... (placements for this advertiser) / (placements in total report)
   H = heterogeneity - undefined (file type, orientation, sizes(?))

not represented here -

* raw occurrences
* channels - every ad/site combo is one channel
** same ad on two sites is two channels, some ads might not be on all sites of this advertiser

cant come up with aggregate score until numbers normalized

M+[C]hannels

TODO: write up detailed explanation of report

*/


/* report format:

Two levels of aggregate

1.  Advertiser1   S.M.A.S.H.

    Advertiser2   S.M.A.S.H.

2.  Advertiser1   Domain 1 x y
                  Domain 2 x y
                  Domain 3 x y

    Advertiser2   Domain 1 x y
                  Domain 2 x y
                  Domain 3 x y

*/

/*

set @startDate = '2012-05-01';
set @endDate = '2012-05-01';
set @domain1id = '2C57FA5A-453C-11E0-ACC5-FC49607E8A75';
set @domain2id = '2C57FA5A-453C-11E0-ACC5-FC49607E8A75';
set @domain3id = '2C57FA5A-453C-11E0-ACC5-FC49607E8A75';

*/

select *,
       /* log10 of a number that ranges from 0 to 100 will give us a number between 0 and 2, so we multiply by 9
       to a number between 0 and 18, (1/5 of the share of 90% of the score) */
       log10(sites) * 9 /* assumes 100 sites, log base should be equal to the square root of the total number of sites */
       +
       /* TODO TODO TODO */
       log10(programs) * 9 /* this should really depend on the time frame and maybe even the total # of distinct ads in the result set */
       +
       log10(ad_area) * 4.5
       +
       log10(10)
       +
       log2(heterogeneity) * 5.5
       as splash /* 10% hetero, 90% evenly divided up against the other five metrics */
from (
select
   c.comp_id
  ,c.company_name
  ,count(distinct imageFileId) as programs
  ,count(distinct hostId) as sites
  ,count(distinct concat(imageFileId, '//', hostId)) as channels
  ,sum(ad_count) as ad_count
  ,sum(ad_area) as ad_area
  ,sum(if(hostId = @domain1id, ad_count, 0)) as domain1s1
  ,sum(if(hostId = @domain2id, ad_count, 0)) as domain2s1
  ,sum(if(hostId = @domain3id, ad_count, 0)) as domain3s1
  ,group_concat(distinct h.title order by h.title separator ', ') as domains_seen_on
  ,count(distinct f.aspect_ratio) + count(distinct substring(f.filename from -4 for 4)) heterogeneity
from
  fct_advertiser_host_month_ad_smash fct
inner join
  company c
on
  c.comp_id = fct.company
    and
  month between @startDate and @endDate
inner join
  data_entry de
on
  fct.imageFileId = de.file
    and
  de.de_status = 1
inner join
  hosts h
on
  hostId = h.id
inner join
  imageFile f
on
  fct.imageFileId = f.id
group by 1, 2
) as x