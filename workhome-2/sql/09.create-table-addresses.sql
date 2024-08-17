create table addresses(
	address_id bigserial primary key,
	street_id bigint not null references streets(street_id),
	name varchar(100) not null,
	long longitude,
	lat latitude,
	description text
);