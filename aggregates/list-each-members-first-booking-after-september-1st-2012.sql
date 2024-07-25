select surname, firstname, memid, min(starttime) from cd.members m
	join cd.bookings b using(memid)
	where starttime >= '2012-09-01'
	group by surname, firstname, memid
	order by memid