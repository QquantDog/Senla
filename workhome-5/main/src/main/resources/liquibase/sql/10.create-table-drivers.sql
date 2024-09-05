create table drivers(
	driver_id bigint primary key references users(user_id),	
	active_city_id bigint not null references cities(city_id),
	is_ready bool not null,
	current_lat latitude,
	current_long longitude
);