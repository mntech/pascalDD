/* Set these session variables in the data access code before running the query

set @startMonth = 'Jan';
set @startYear = 2013;
set @endMonth = 'Jan';
set @endYear = 2013;
set @keywords = '';
set @domain = '';
set @advertiser = 'nature';
set @domainCount = 0;
set @aspectRatio = '';
set @imageNum = '';
set @createdSince = null;
set @removeHouseAds = 0;

set @startDate = str_to_date(concat('1,',@startMonth,',',@startYear),'%d,%M,%Y');
set @endDate = (str_to_date(concat('1,',@endMonth,',',@endYear),'%d,%M,%Y') + interval 1 month) - interval 1 day;
set @daysInSearchPeriod = datediff(@endDate, @startDate);

drop table if exists tmp_adsearch_domains;
create temporary table tmp_adsearch_domains (id char(36));

*/

/*
| id | select_type        | table                | type   | possible_keys       | key         | key_len | ref                        | rows   | Extra                                        |
+----+--------------------+----------------------+--------+---------------------+-------------+---------+----------------------------+--------+----------------------------------------------+
|  1 | PRIMARY            | agg                  | ALL    | image_num           | NULL        | NULL    | NULL                       | 137722 | Using where; Using temporary; Using filesort |
|  1 | PRIMARY            | d                    | ref    | file_number,company | file_number | 5       | agora_scrape.agg.image_num |      1 | Using where                                  |
|  1 | PRIMARY            | f                    | eq_ref | image_num           | image_num   | 4       | agora_scrape.agg.image_num |      1 |                                              |
|  1 | PRIMARY            | c                    | eq_ref | PRIMARY             | PRIMARY     | 4       | agora_scrape.d.company     |      1 | Using where                                  |
|  1 | PRIMARY            | hc                   | ref    | comp_id             | comp_id     | 4       | agora_scrape.d.company     |      1 | Using index                                  |
|  2 | DEPENDENT SUBQUERY | tmp_adsearch_domains | ALL    | NULL                | NULL        | NULL    | NULL                       |      1 | Using where                                  |
*/

select
        agg.id,
        date_format(f.createdate, '%M %e, %Y') as createdate,
        size,
        filename,
        date_format(derived_lastDate, '%M %e, %Y') as lastDate,
        product,
        description,
        company_name, /* 8 */
        agg.image_num, /* 9 */
        aspect_ratio,
        greatest(0, round(100 * datediff(createdate, @startDate) / @daysInSearchPeriod, 0)) as begin_percent,
        least(100, round(100 * datediff(derived_lastDate, @startDate) / @daysInSearchPeriod, 0)) as end_percent,
        /* only count the ads that match the domain filter */
        cast(sum(case
                 when (@domain = '' or agg.path like concat('%',@domain,'%')) and
                      (@domainCount = 0 or agg.hostId in (select id from tmp_adsearch_domains)) and
		      (@removeHouseAds = 0 or hc.hostId is null)
                 then agg.ad_count else 0 end
                 ) as unsigned integer) as ad_count,
        group_concat(distinct agg.path order by agg.path separator ', ') as site_list,
        group_concat(distinct agg.hostId order by agg.hostId separator ',') as host_list
from
      data_entry d
inner join
      fct_ad_month_site_placements agg
on
        d.file_number = agg.image_num
                      and
        de_status = 1 /* not hold / non-ad */
                      and
        (@keywords = '' or match(description, product) against (@keywords in boolean mode))
                      and
        (@startYear = 0 or agg.month between @startDate and @endDate)
inner join
        imageFile f
on
        agg.image_num = f.image_num
                      and
        (@aspectRatio = '' or f.aspect_ratio = @aspectRatio)
                      and
        (@imageNum = '' or f.image_num = @imageNum)
                      and
        (@createdSince is null or f.createdate >= @createdSince)
inner join
      company c
on
        d.company = c.comp_id and
        (@advertiser = '' or lower(c.company_name) like lower(concat('%',@advertiser,'%')))
left join
     host_company_assoc hc
on
     c.comp_id = hc.comp_id
     	       and
     agg.hostId = hc.hostId
group by 1,2,3,4,5,6,7,8,9,10,11,12
having ad_count > 0
order by 8
