select name,
		case
			when monthlymaintenance <= 100 then 'cheap'
			else 'expensive'
			end as cost
		from cd.facilities