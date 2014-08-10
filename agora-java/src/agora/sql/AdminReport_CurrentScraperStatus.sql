select
  h.path, count(p.id) as urls
from
  hosts h
left join
  pulls p
on
  h.id = p.hostId
    and
  h.active = 1
    and
  p.run_id = (select max(run_id) from scraper_run)
group by 1
order by 2