select name, sum(case memid 
				 when 0 
				 	then slots*guestcost 
				 	else slots*membercost 
				 end) as revenue from cd.facilities f
	join cd.bookings b on b.facid = f.facid
	group by name
	order by revenue