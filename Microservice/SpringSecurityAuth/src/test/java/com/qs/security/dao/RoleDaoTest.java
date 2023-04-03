package com.qs.security.dao;

import com.qs.security.entity.Role;
import com.qs.security.entity.User;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(value = "test")
@SpringBootTest
public class RoleDaoTest {
    @Autowired
    RoleDao roleDao;

    SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Test
    @Transactional
    public void test_getRoleByName() {
        Role mockRole = Role.builder()
                .role_description("hr roles")
                .role_name("HR")
                .build();

        Role role = roleDao.getRoleByName(mockRole.getRole_name());
        role.setRole_id(null);
        role.setCreate_date(null);
        role.setLast_modification_date(null);

        assertEquals(mockRole, role);
    }

}
