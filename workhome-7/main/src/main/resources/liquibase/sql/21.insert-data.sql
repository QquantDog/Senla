insert into rate_tiers(tier_name, description) values('economic', 'best balance between comfort and price'),
                                                     ('comfort',  'comfort description'),
                                                     ('lux', 	  'top-tier service');

insert into vehicle_brands(brand_name) values('Audi'),('Volkswagen'),('Skoda'),('Toyota');

insert into vehicles(brand_id, vehicle_model, tier_id, seats_number) values(1, 'TT', 1, 4),(2, 'Passat', 1, 4),(3, 'Rapid', 2, 4),(4, 'Corolla', 2, 4);

insert into "privileges"(privilege_code, privilege_name, description) values('usr', 'user', 'default'),('adm', 'admin', 'full');

insert into roles(role_name) values('customer'), ('driver'), ('admin');

insert into roles_privileges_join(privilege_id, role_id) values(1, 1),(1, 2),(2, 3);

insert into cities(city_name) values('Grodno'), ('Minsk');

insert into rates(city_id, rate_tier_id, init_price, rate_per_km, paid_waiting_per_minute, free_time_in_seconds)
values(1, 1, 2.0, 1.0, 0.4, 120),(2, 1, 3.0, 1.5, 0.6, 120),
      (1, 2, 2.2, 1.2, 0.45, 180),(2, 2, 3.3, 1.8, 0.7, 180);

insert into users(role_id, first_name, last_name, email, hashed_password, phone_number, registration_date) values
                                                                                                               (1, 'Max', 'qqq', 'max@gmail.com', 'hashedPWD1', '+375-22-341-53-21', now() - interval '5 months'),
                                                                                                               (1, 'Vlad', 'www', 'vlad@gmail.com', 'hashedPWD2', '+375-44-453-91-88', now() - interval '7 months'),

                                                                                                               (2, 'Oleg', 'aaa', 'pleg@gmail.com', 'hashedPWD3', '+375-72-3324214', now() - interval '12 months'),
                                                                                                               (2, 'Grisha', 'ccc', 'grisha@gmail.com', 'hashedPWD4', '+123-45-678-90-12', now() - interval '15 months'),

                                                                                                               (2, 'Driver_1', 'vvv', 'driver1@gmail.com', 'hashedPWD5', '+123-63-173-90-12', now() - interval '15 months'),
                                                                                                               (2, 'Driver_2', 'cvx', 'driver2@gmail.com', 'hashedPWD6', '+321-45-873-90-12', now() - interval '14 months'),

                                                                                                               (3, 'Admin', '----', 'admin@gmail.com', 'hashedPWD6', '+324-53-23-52-52', now() - interval '3 months'),
                                                                                                               (2, 'TestDriver', 'hmty', 'wow@gmail.com', '---', '+645-22-341-53-21', now() - interval '4 months'),
                                                                                                               (1, 'TestCustomer', 'hjkt', 'meow@gmail.com', '---', '+456-22-341-53-21', now() - interval '7 months'),
                                                                                                               (2, 'NearDriver1', 'oooo', 'wow1@gmail.com', '---', '+645-22-b41-53-21', now() - interval '4 months'),
                                                                                                               (2, 'NearDriver2', 'oooo', 'wow2@gmail.com', '---', '+645-22-a41-53-21', now() - interval '4 months');

insert into drivers(driver_id, is_on_shift, is_on_ride, current_lat, current_long, current_point) values
                                                                                                      (3, false, false, null, null, null),
                                                                                                      (4, false, false, null, null, null),
                                                                                                      (5, true, false, 54.6126, 23.5126, 'POINT(54.6126 23.5126)'),
                                                                                                      (6, true, true, 54.3562, 23.8543, 'POINT(54.3562 23.8543)'),
                                                                                                      (8, false, false, null, null, null),
                                                                                                      (10, true, false, 54.3, 23.5, 'POINT(54.3 23.5)'),
                                                                                                      (11, true, false, 54.35, 23.6, 'POINT(54.35 23.6)');

insert into taxi_companies(name, telephone, park_code) values('First taxopark_1', '+234-432-34-23', 7320), ('Second_taxopark_2', '+aaaaaa', 4954);

insert into driver_registry(driver_id, company_id, registration_date, registration_expiration_date) values(3, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval), (4, 2, now()::date - '5 year'::interval, now()::date + '5 year'::interval),
                                                                                                          (5, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval), (6, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval),
                                                                                                          (3, 2, now()::date - '5 year'::interval, now()::date + '5 year'::interval),
                                                                                                          (10, 2, now()::date - '5 year'::interval, now()::date + '5 year'::interval), (10, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval),
                                                                                                          (11, 2, now()::date - '5 year'::interval, now()::date + '5 year'::interval), (11, 1, now()::date - '5 year'::interval, now()::date + '5 year'::interval);

-- текущие такси
insert into cabs(registered_company_id, is_on_shift, vin, manufacture_date, color_description, vehicle_id, license_plate) values
                                                                                                                              (1, true, 'vin1', '2020-01-02'::date, 'yellow',  1, '1234-mb-1'),
                                                                                                                              (1, true, 'vin2', '2016-02-03'::date, 'black',  2, '4321-hy-4'),
                                                                                                                              (2, false, 'vin3', '2019-04-05'::date, 'gray',  3, '5631-mb-1'),
                                                                                                                              (2, false, 'vin4', '2020-01-01'::date, 'yellow', 1, '1235-mb-2'),
                                                                                                                              (1, true, 'vin5', '2020-01-01'::date, 'white', 1, '1235-mb-3'),
                                                                                                                              (2, true, 'vin6', '2020-01-01'::date, 'white', 2, '1235-mb-4');

insert into shifts(city_rate_id, cab_id, driver_id, starttime, endtime) values
                                                                       (1, 1, 3, '2020-01-01 10:10:10'::timestamp, '2020-01-01 18:10:10'::timestamp),
                                                                       (2, 2, 3, '2020-01-02 10:10:10'::timestamp, '2020-01-02 18:10:10'::timestamp),
                                                                       (3, 3, 4, '2021-03-01 14:00:00'::timestamp, '2021-03-01 21:00:00'::timestamp),
                                                                       (4, 3, 4, '2021-03-01 14:00:00'::timestamp, '2021-03-01 21:00:00'::timestamp),
                                                                       (1, 2, 5, now() - interval '180 min', null), -- этот в активном поиске
                                                                       (2, 1, 6, now() - interval '120 min', null),
                                                                       (2, 5, 10, now() - interval '180 min', null), -- этот в активном поиске
                                                                       (2, 6, 11, now() - interval '120 min', null); -- этот везет пассажира

insert into promocodes(promocode_code, discount_value, start_date, end_date, description) values
                                                                                              ('DISCOUNT20', 0.2, '2025-01-01'::date, '2025-01-03'::date, '20 percent discount'),
                                                                                              ('DISCOUNT30', 0.3, '2025-05-01'::date, '2025-05-03'::date, '30 percent discount');



insert into customer_ratings(rating, created_at, updated_at, "comment") values
                                                                            (5.0, '2020-01-02 10:00:00', null, 'отличная поездка'),
                                                                            (4.0, '2020-01-01 14:23:20', null, 'непристегнутый ремень у водителя');

insert into payments(method, overall_price) values
                                                                                     ('cash', 8.0),
                                                                                     ('card', 7.4),
                                                                                     ('cash', 8.7),
                                                                                     ('cash', null),
                                                                                     ('cash', null);

-- пока всего 2 кастомера - будет 1 активный, 2 поиске - потом добавим и пойдет
insert into rides(customer_id, shift_id, promocode_id, customer_rating_id, payment_id, accepted_rate_id, promocode_enter_code, ride_tip, ride_distance_expected_meters, ride_distance_actual_meters, start_point_lat, start_point_long, start_point, end_point_lat, end_point_long, end_point, created_at, ride_accepted_at, ride_driver_waiting, ride_starttime, ride_endtime, status, ride_expected_price, ride_actual_price) values
                                                                                                                                                                                                                                                                                                                        (1, 1, null, 1,    1, 1, 'abc',        2.5, 5500.0, 5675.0,'53.34', '23.49', 'POINT(53.34 23.49)', '53.65', '23.43', 'POINT(53.65 23.43)', '2020-01-01 12:00:00', '2020-01-01 12:05:00', '2020-01-01 12:06:00', '2020-01-01 12:15:00', '2020-01-01 12:30:00', 'completed', 5.5, 5.6),
                                                                                                                                                                                                                                                                                                                        (1, 1, 1,    2,    2, 2, 'DISCOUNT20', null, 4500.0, 4489.0,'53.14', '23.38', 'POINT(53.14 23.38)', '53.17', '23.42', 'POINT(53.17 23.42)', '2020-01-01 14:00:00', '2020-01-01 14:07:30', '2020-01-01 14:10:50', '2020-01-01 14:17:20', '2020-01-01 14:28:20', 'completed', 7.4, 7.2),
                                                                                                                                                                                                                                                                                                                        (2, 3, null, null, 3, 3, null,         2.0, 4600.0, 4710.0,'53.86', '27.23', 'POINT(53.86 27.23)', '53.79', '27.54', 'POINT(53.79 27.54)', '2021-03-01 15:00:00', '2021-03-01 15:07:30', '2021-03-01 15:10:50', '2021-03-01 15:16:20', '2021-03-01 15:23:20', 'completed', 6.7, 6.8),
                                                                                                                                                                                                                                                                                                                        (1, 5, null, null, 4, 4, null,         null, 5500.0, null,  '53.34', '23.49', 'POINT(53.34 23.49)', '53.65', '23.43', 'POINT(53.65 23.43)', now() - interval '3 min', null, null, null, null, 'accepted', 8.6, null),
                                                                                                                                                                                                                                                                                                                        (2, 6, null, null, 5, 1, null,         null, 4500.0, null,  '53.14', '23.38', 'POINT(53.14 23.38)', '53.17', '23.42', 'POINT(53.17 23.42)', now() - interval '12 min', now() - interval '5 min', now() - interval '2 min', now() - interval '2 min', null, 'in-way', 9.2, null);
