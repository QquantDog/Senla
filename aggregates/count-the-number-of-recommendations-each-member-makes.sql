select m2.recommendedby, count(m2.memid) from cd.members m1
	join cd.members m2 on m1.memid = m2.recommendedby
	group by m2.recommendedby
	order by m2.recommendedby