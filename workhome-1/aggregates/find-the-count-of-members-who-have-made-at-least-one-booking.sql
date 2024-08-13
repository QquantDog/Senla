select count(distinct m.memid) from cd.members m
	join cd.bookings b on b.memid = m.memid