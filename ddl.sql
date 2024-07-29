create schema if not exists cd;

create table if not exists cd.members(
	memid serial primary key,
	surname varchar(200),
	firstname varchar(200),
	address varchar(300),
	zipcode int,
	telephone varchar(20),
	recommendedby int references cd.members(memid),
	joindate timestamp
);

create table if not exists cd.facilities(
	facid serial primary key,
	name varchar(100),
	membercost numeric,
	guestcost numeric,
	initialoutlay numeric,
	monthlymaintenance numeric
);

create table if not exists cd.bookings(
	bookid serial primary key,
	facid int references cd.facilities(facid) on delete cascade,
	memid int references cd.members(memid) on delete cascade,
	starttime timestamp,
	slots int
);