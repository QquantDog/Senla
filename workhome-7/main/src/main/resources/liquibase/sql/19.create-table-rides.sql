create table rides(
      ride_id bigserial primary key,
      customer_id bigint not null references users(user_id),
      shift_id bigint references shifts(shift_id),
      promocode_id bigint references promocodes(promocode_id),

      customer_rating_id bigint unique references customer_ratings(rating_id),
      payment_id bigint not null unique references payments(payment_id),

--    к сожалению поле city_id необходимо так как тарифы привязаны пока к городу, а не к
      accepted_rate_id bigint not null references rates(rate_id),


      promocode_enter_code varchar(100),
      ride_tip numeric,

      ride_distance_expected_meters numeric not null,
      ride_distance_actual_meters numeric,

      start_point_long longitude not null,
      start_point_lat latitude not null,
      start_point geometry(Point, 4326) not null,
      end_point_long longitude not null,
      end_point_lat latitude not null,
      end_point geometry(Point, 4326) not null,
      created_at timestamp with time zone not null default now(),
      ride_accepted_at timestamp with time zone,
      ride_driver_waiting timestamp with time zone,
      ride_starttime timestamp with time zone,
      ride_endtime timestamp with time zone,
      status ride_status,
      ride_expected_price numeric not null,
      ride_actual_price numeric,
      constraint ride_tip_non_negative check (ride_tip > 0.0),
      constraint non_negative_distance_expected check (ride_distance_expected_meters >= 0.0),
      constraint non_negative_distance_actual check (ride_distance_actual_meters >= 0.0),
      constraint non_negative_price check (ride_expected_price >= 0.0 and ride_actual_price >= 0.0)
);