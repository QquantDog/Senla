insert into vehicle_brands(brand_name) values('Audi'),('Volkswagen'),('Skoda'),('Toyota');

insert into vehicles(brand_id, vehicle_model) values(1, 'TT'),(2, 'Passat'),(3, 'Rapid'),(4, 'Corolla');

insert into "privileges"(privilege_code, privilege_name, description) values('usr', 'user_name', 'default'),('adm', 'admin', 'full');

insert into roles(role_name) values('customer'), ('driver'), ('admin');

insert into roles_privileges_join(privilege_id, role_id) values(1, 1),(1, 2),(2, 3);

insert into cities(city_name) values('Grodno'), ('Minsk');

insert into city_rates(city_id, init_price, rate_per_km, paid_waiting_per_minute, free_time_in_seconds)
						values(1, 2.0, 1.0, 0.4, 120),(2, 3.0, 1.5, 0.6, 120);

insert into streets(street_name, city_id) values('Sovetskaya', 1),('Rumlevo', 1),('Minskaya', 2);

insert into addresses(street_id, name, long, lat, description) values
						(1, 'D. 13', '43.53', '46.36', 'House near main road'),
						(2, 'D. 23', '42.43', '47.32', 'Industrial building'),
						(3, 'D. 107', '51.53', '42.36', 'Hotel');
insert into users(role_id, first_name, last_name, email, hashed_password, phone_number) values
					(1, 'Max', 'qqq', 'max@gmail.com', 'hashedPWD1', '+375-22-341-53-21'),
					(1, 'Vlad', 'www', 'vlad@gmail.com', 'hashedPWD2', '+375-44-453-91-88'),
					(2, 'Oleg', 'aaa', 'pleg@gmail.com', 'hashedPWD3', '+375-72-3324214'),
					(2, 'Grisha', 'ccc', 'grisha@gmail.com', 'hashedPWD4', '+123-45-678-90-12'),
					(3, 'Admin', '----', 'admin@gmail.com', 'hashedPWD5', '+324-53-23-52-52');
insert into customers(customer_id, home_address_id, current_lat, current_long) values
					(1, null, '23.35', '34.52'),
					(2, null, '64.46', '65.23');
insert into drivers(driver_id, active_city_id, is_ready, current_lat, current_long) values
					(3, 1, true, '34.52', '55.34'),
					(4, 2, true, '52.35', '35.23');
insert into cabs(vin, manufacture_date, color_description, city_id, vehicle_id, license_plate, park_code) values
						('vin1', '2020-01-02'::date, 'yellow', 1, 1, '1234-mb-1', '7420'),
						('vin2', '2016-02-03'::date, 'black', 1, 2, '4321-hy-4', '9120'),
						('vin3', '2019-04-05'::date, 'gray', 2, 3, '5631-mb-1', '6400'),
						('vin4', '2020-01-01'::date, 'yellow', 1, 1, '1235-mb-1', '7420');
insert into shifts(cab_id, driver_id, starttime, endtime) values
					(1, 3, '2020-01-01 10:10:10'::timestamp, '2020-01-01 18:10:10'::timestamp),
					(2, 3, '2020-01-02 10:10:10'::timestamp, '2020-01-02 18:10:10'::timestamp),
					(3, 4, '2021-03-01 14:00:00'::timestamp, '2021-03-01 21:00:00'::timestamp),
					(3, 4, '2021-03-01 14:00:00'::timestamp, '2021-03-01 21:00:00'::timestamp);
insert into cards(customer_id, card_number, expiration_date, card_holder_name) values
					(1, '1234-1234-4321-4321', '2027-05-01'::date, 'NAME SURNAME');
insert into promocodes(promocode_code, discount_value, start_date, end_date, description) values
					('DISCOUNT20', 0.2, '2025-01-01'::date, '2025-01-03'::date, '20 percent discount'),
					('DISCOUNT30', 0.3, '2025-05-01'::date, '2025-05-03'::date, '30 percent discount');
insert into rides(shift_id, customer_id, promocode_id, promocode_enter_code, ride_tip, ride_distance_meters, start_point_long, start_point_lat, end_point_long, end_point_lat, created_at, ride_driver_waiting, ride_starttime, ride_endtime, status) values
					(1, 1, null, 'abc', 2.5, 5500.0, '34.34', '23.52', '35.65', '22.43', '2020-01-01 12:00:00', '2020-01-01 12:05:00', '2020-01-01 12:06:00', '2020-01-01 12:20:00', 'completed'),
					(1, 1, 1, 'DISCOUNT20', 0.0, 4500.0, '36.14', '26.34', '36.17', '26.43', '2020-01-01 14:00:00', '2020-01-01 14:07:30', '2020-01-01 14:10:50', '2020-01-01 14:23:20', 'completed');
insert into customer_ratings(ride_id, rating, created_at, "comment") values
					(1, 5.0, '2020-01-02 10:00:00', 'отличная поездка');