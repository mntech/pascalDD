select
  path,
  min(month_first) as min_month,
  max(month_last) as max_month,
  sum(ad_count) as ad_count,
  group_concat(distinct depths order by depths separator ', ') as depths
from
  fct_ad_month_site_placements agg
where
  agg.image_num = ?
group by 1
order by 1

