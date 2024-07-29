select distinct CONCAT(firstname, ' ', surname) as member, name as facility from cd.members m
	join cd.bookings b using (memid)
	join cd.facilities f using (facid)
	where f.name ILIKE '%tennis court%'
	order by member, facility