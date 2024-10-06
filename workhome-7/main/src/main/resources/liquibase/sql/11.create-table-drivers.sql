create table drivers(
    driver_id bigint primary key references users(user_id),
    is_on_shift bool not null,
    is_on_ride bool not null,
    current_lat latitude,
    current_long longitude,
    current_point geometry(Point, 4326)
);