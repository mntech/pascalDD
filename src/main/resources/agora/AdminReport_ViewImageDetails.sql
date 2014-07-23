select
	f.id, f.image_num, f.md5, f.size, f.createdate, f.filename, f.aspect_ratio,
	f.derived_lastDate,
	company_name, description, product, de_status, username
from imageFile f
left join data_entry d on f.image_num = d.file_number
left join company c on d.company = c.comp_id
left join users u on d.de_user_id = u.user_id
where f.image_num = ? or f.id = ?
