select starttime from cd.bookings b
	join cd.members m using (memid)
	where firstname = 'David' and surname = 'Farrell'