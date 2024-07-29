select f.facid, sum(slots) as "Total slots" from cd.facilities f
	join cd.bookings b on b.facid = f.facid
	group by f.facid
	order by "Total slots" desc
	limit 1