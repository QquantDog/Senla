select b.facid, sum(b.slots) from cd.bookings b
	join cd.facilities f on f.facid = b.facid
	where starttime >= '2012-09-01 00:00:00' and starttime < '2012-10-01 00:00:00'
	group by b.facid
	order by sum(b.slots)