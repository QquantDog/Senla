with recursive top as(
	select memid, recommendedby, firstname, surname
 	from cd.members
  	where memid = 27

  	union all

  	select m.memid, m.recommendedby, m.firstname, m.surname
  	from cd.members m join top t
  		on m.memid = t.recommendedby
)
select memid, firstname, surname from top
	offset 1
