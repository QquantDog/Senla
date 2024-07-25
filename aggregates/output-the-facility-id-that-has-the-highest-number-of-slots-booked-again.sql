select facid, max(total) from
  (select facid, sum(slots) as total from cd.bookings
	  group by facid) as subq
group by facid