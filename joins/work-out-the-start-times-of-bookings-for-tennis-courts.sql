select starttime as start, name from cd.bookings b
	join cd.facilities f using (facid)
	where starttime >= '2012-09-21 00:00:00' and starttime < '2012-09-22 00:00:00'
		and name ILIKE '%tennis court%'
	order by start