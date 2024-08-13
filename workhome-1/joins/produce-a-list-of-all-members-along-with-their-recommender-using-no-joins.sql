--уииииииии
select distinct concat(m1.firstname, ' ', m1.surname) as member,
		(
			select concat(s.firstname, ' ', s.surname) from cd.members s
		  		where m1.recommendedby = s.memid
		) as recommender from cd.members m1


--select distinct concat(m1.firstname, ' ', m1.surname) as member,
--		concat(m2.firstname, ' ', m2.surname) as recommender
--			from cd.members m1
--			left join cd.members m2 on m1.recommendedby = m2.memid
--		order by member, recommender