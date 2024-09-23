create table shifts(
	shift_id bigserial primary key,
	cab_id bigint not null references cabs(cab_id),
	driver_id bigint not null references drivers(driver_id),
	starttime timestamp not null default now(),
	endtime timestamp
);