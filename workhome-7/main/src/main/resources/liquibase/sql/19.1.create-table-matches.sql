create table matches(
     match_id bigserial primary key,
     ride_id bigint not null references rides(ride_id),
     shift_id bigint not null references shifts(shift_id),
     upper_threshold timestamp with time zone not null
);