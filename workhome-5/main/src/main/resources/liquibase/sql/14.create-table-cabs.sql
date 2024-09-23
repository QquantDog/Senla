create table cabs(
	vin varchar(17) unique not null,
	manufacture_date date not null,
	color_description varchar(100) not null,
	city_id bigint not null references cities(city_id),
	cab_id bigserial primary key,
	vehicle_id bigserial not null references vehicles(vehicle_id),
	license_plate varchar(50) not null unique,
	park_code varchar(20)
);