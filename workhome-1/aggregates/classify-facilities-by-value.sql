select name, case ntile(3) over(order by total desc)
				when 1 then 'high'
				when 2 then 'average'
				else 'low'
			 end revenue
	   from
	   (select name, sum(
						case
							when memid = 0 then guestcost * slots
							else membercost * slots
						end
					   ) as total from cd.members m
	  								join cd.bookings b using (memid)
	  								join cd.facilities f using (facid)
				group by name) as subq
		order by ntile(3) over(order by total desc), name