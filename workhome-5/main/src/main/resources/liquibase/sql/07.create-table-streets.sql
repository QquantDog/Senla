create table streets(
	street_id bigserial primary key,
	city_id bigint not null references cities(city_id),
	street_name varchar(200) not null
);