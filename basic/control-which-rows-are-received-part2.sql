select facid, name, membercost, monthlymaintenance from cd.facilities
	where membercost < 1.0/50 * monthlymaintenance and membercost != 0