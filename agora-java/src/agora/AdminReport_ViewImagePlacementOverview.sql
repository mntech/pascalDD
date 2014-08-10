select r.run_date, h.path, 'src', sum(fct.ad_count)
from imageFile f
inner join fct_run_host_depth_ad fct on fct.imageFileId = f.id and (f.image_num = ? or f.id = ?)
inner join scraper_run r on fct.run_id = r.run_id
inner join hosts h on fct.hostId = h.id
group by 1, 2, 3 order by 1 desc, 2, 3
;