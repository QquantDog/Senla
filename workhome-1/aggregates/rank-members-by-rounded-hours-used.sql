select firstname,
		surname,
		round(sum(slots * 0.5),-1) as hours,
		rank() over(order by round(sum(slots * 0.5),-1) desc)
			from cd.members m
	join cd.bookings b using (memid)
	join cd.facilities f using (facid)
	group by firstname, surname
	order by rank, surname, firstname