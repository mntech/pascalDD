/*
* Get new imageFile records from scraper db
*/

insert into imageFile
select * from agora_remote.imageFile where image_num >
(select max(image_num) from imageFile);