select distinct m1.firstname, m1.surname from cd.members m1
	join cd.members m2 on m1.memid = m2.recommendedby
	order by m1.surname, m1.firstname