select member, facility, cost
	   from (select concat(firstname, ' ', surname) as member, name as facility,
			 case memid
	   			when 0 then slots * guestcost
				else slots * membercost
	   		 end as cost
				from cd.members m
				   join cd.bookings b using(memid)
				   join cd.facilities f using(facid)
				 where starttime >= '2012-09-14' and starttime < '2012-09-15' and
				(
				 (memid  = 0 and slots  * guestcost > 30)
					or
				 (memid != 0 and slots  * membercost > 30)
				)
			) as subq
	   order by cost desc