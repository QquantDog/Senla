select 	dategen.date,
	(
		select sum(case
			when memid = 0 then slots * facs.guestcost
			else slots * membercost
		end) as rev

		from cd.bookings bks
		inner join cd.facilities facs
			on bks.facid = facs.facid
		where bks.starttime > dategen.date - interval '14 days'
			and bks.starttime < dategen.date + interval '1 day'
	)/15 as revenue
	from
	(
		select 	cast(generate_series(timestamp '2012-08-01',
			'2012-08-31','1 day') as date) as date
	)  as dategen
order by dategen.date;

--нерабочее решение не сходится
--select day as date, sum(sum) over(order by day rows between 14 preceding and current row)/15 as revenue from
--	(select sum(case memid
--					when 0 then guestcost*slots
--					else membercost*slots
--			 end), starttime::date as day
--			 from cd.facilities f
--						join cd.bookings b using (facid)
--			  where starttime::date >= '2012-07-01' and starttime::date < '2012-09-01'
--			  group by starttime::date) as sum_per_day
--where day >= '2012-08-01'