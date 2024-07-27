with outq as(
	select facid, sum(slots) from cd.bookings b
  	join cd.facilities f using (facid)
  group by facid
)
select facid, sum from outq
	where sum = (select max(sum) from outq)