select name, rank() over(order by revenue desc) from 
	(
	  select name, sum(
						case 
							when memid = 0 then guestcost * slots
							else membercost * slots
						end
					   ) as revenue from cd.members m
	  								join cd.bookings b using (memid)
	  								join cd.facilities f using (facid)
	  					group by name
	) as subq
	limit 3