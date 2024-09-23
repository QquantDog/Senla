create table cards(
	card_id bigserial primary key,
	customer_id bigint references customers(customer_id),
	card_number varchar(50) not null unique,
	expiration_date date not null,
	card_holder_name varchar(200) not null,
	constraint expiration_date_is_actual check (expiration_date > current_date)
);