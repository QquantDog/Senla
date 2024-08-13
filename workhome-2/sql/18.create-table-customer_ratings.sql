create table customer_ratings(
	rating_id bigserial primary key,
	ride_id bigint unique references rides(ride_id),
	rating numeric,
	created_at timestamp not null default now(),
	comment text,
	constraint rating_between_1_and_5 check (rating >= 1.0 and rating <= 5.0)
);