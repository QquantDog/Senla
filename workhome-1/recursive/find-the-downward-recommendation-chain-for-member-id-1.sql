with recursive down as(
 	select memid, firstname, surname
  	from cd.members
  	where memid = 1

  	union all

  	select m.memid, m.firstname, m.surname
  	from cd.members m
  	join down on m.recommendedby = down.memid
)
select * from down
order by memid
	offset 1