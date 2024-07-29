select facid, name, round((0.5*sum(slots)),2) from cd.facilities f
	join cd.bookings b using (facid)
	group by facid, name
	order by facid