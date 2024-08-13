select name, initialoutlay / (brutto - monthlymaintenance) as months from
  (select f.facid, sum(case memid
				  when 0 then guestcost*slots
				  else membercost*slots
			   end)/3 as brutto
			   from cd.facilities f
					  join cd.bookings b using (facid)
					  group by f.facid) as subq
  join cd.facilities out on subq.facid = out.facid
  order by name