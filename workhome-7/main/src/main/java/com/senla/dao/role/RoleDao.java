package com.senla.dao.role;

import com.senla.models.role.Role;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RoleDao extends AbstractLongDao<Role> implements IRoleDao{

    @Override
    @PostConstruct
    protected void init() {
        this.clazz = Role.class;
    }
}
