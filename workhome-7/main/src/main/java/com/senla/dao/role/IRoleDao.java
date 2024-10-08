package com.senla.dao.role;

import com.senla.models.role.Role;
import com.senla.util.dao.GenericDao;
import org.springframework.stereotype.Component;

@Component
public interface IRoleDao extends GenericDao<Role, Long> {

}
