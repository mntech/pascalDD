select
        distinct h.id, h.path, h.active, h.title, h.comment, h.depth
from
        hosts h
left join
     pulls p
on
        h.id = p.hostId
                 and
        p.run_id = ?
where
        p.id is null
	     and
	h.active = 1
