select f.facid, sum(slots) from cd.facilities f
	join cd.bookings b on b.facid = f.facid
	group by f.facid
	having sum(slots) > 1000
	order by f.facid