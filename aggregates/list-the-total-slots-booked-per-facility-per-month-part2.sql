-- чето душный запрос
select * from (
  select facid, extract(month from starttime) as month, sum(slots) from cd.bookings b
	  where starttime >= '2012-01-01' and starttime < '2013-01-01'
	  group by facid, extract(month from starttime)
  union
  select facid, null, sum(slots) from cd.bookings b
	  where starttime >= '2012-01-01' and starttime < '2013-01-01'
	  group by facid
  union
  select null, null, sum(slots) from cd.bookings b
	  where starttime >= '2012-01-01' and starttime < '2013-01-01') as data
order by facid, month
