create table vehicles(
	vehicle_id bigserial primary key,
	brand_id bigint not null references vehicle_brands(brand_id),
	vehicle_model varchar(200) not null
);