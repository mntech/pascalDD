/* flow new data to DE */
insert into ftp_images
    (ftp_image_id, create_time, stored_fold_path, image_name, processed_status)
select image_num, now(), concat('data/', filename), id, 0
from imageFile
where is_not_ad = 0
and image_num not in (select ftp_image_id from ftp_images);
