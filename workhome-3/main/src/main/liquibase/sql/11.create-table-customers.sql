create table customers(
	customer_id bigint primary key references users(user_id), 
	home_address_id bigint references addresses(address_id),
	current_lat latitude,
	current_long longitude
);
