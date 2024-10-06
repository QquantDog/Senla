create domain user_role as varchar(20) not null check (value in ('customer', 'driver', 'admin'));
create domain longitude as numeric check (value >= -90.0 and value <= 90.0);
create domain latitude  as numeric check (value >= -180.0 and value < 180.0);
create domain ride_status as varchar(20) not null
	check (value in ('pending', 'accepted', 'waiting-client', 'in-way', 'completed', 'cancelled'));