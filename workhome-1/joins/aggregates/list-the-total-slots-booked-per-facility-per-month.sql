select facid, extract(month from starttime), sum(slots) as "Total slots" from cd.bookings b
	where starttime >= '2012-01-01' and starttime < '2013-01-01'
	group by facid, extract(month from starttime)
	order by facid, extract(month from starttime)