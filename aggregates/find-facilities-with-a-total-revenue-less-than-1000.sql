-- первый способ делает все в 1 селекте - проблема в том что композитная агрегатная функция не может иметь
-- алиас сортировки(в having by) потому что запрос еще не выполнился
select name, sum(case memid
				 when 0
				 	then slots*guestcost
				 	else slots*membercost
				 end) as revenue from cd.facilities f
	join cd.bookings b on b.facid = f.facid
	group by name
	having sum(case memid
				 when 0
				 	then slots*guestcost
				 	else slots*membercost
				 end) < 1000
	order by revenue

