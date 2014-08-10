select
	r.run_id,
	run_date,
	count(i.id)
from
	scraper_run r
inner join
      pulls p
      on p.run_id = r.run_id
left join
      image i
      on i.pullId = p.id
where
	run_date > current_date - interval 2 week
group by 1,2
order by 1 desc
