explain select * from users u
	join roles on roles.role_id = u.role_id
	join roles_privileges_join rp on rp.role_id = roles.role_id
	join "privileges" p on p.privilege_id = rp.privilege_id
	join customers c on c.customer_id = u.user_id
	join rides r on r.customer_id = c.customer_id
	join shifts s on s.shift_id = r.shift_id
	join promocodes on promocodes.promocode_id = r.promocode_id
	join drivers d on d.driver_id = s.driver_id
	join addresses a on a.address_id = c.home_address_id
	join cabs on cabs.cab_id = s.cab_id
	join streets on streets.street_id = a.street_id
	join cities on cities.city_id = streets.street_id
	join vehicles v on v.vehicle_id = cabs.vehicle_id