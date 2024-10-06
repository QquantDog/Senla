create table rate_tiers(
       tier_id bigserial primary key,
       tier_name cab_type not null,
       description text
);