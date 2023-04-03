package com.qs.security.dao;

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
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Test
    @Transactional
    public void test_getUserByName() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();
        mockUser.setUser_id(userDao.createNewUser(mockUser, false, roleDao));
        User user = userDao.getUserByName("admin");

        assertEquals(mockUser.getUser_id(), user.getUser_id());
        assertEquals(mockUser.getUsername(), user.getUsername());
        assertEquals(mockUser.getPassword(), user.getPassword());
        assertEquals(mockUser.getEmail(), user.getEmail());
    }

    @Test
    @Transactional
    public void test_loadUserByName() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();
        mockUser.setUser_id(userDao.createNewUser(mockUser, false, roleDao));
        User user = userDao.loadUserByUsername("admin");

        assertEquals(mockUser.getUser_id(), user.getUser_id());
        assertEquals(mockUser.getUsername(), user.getUsername());
        assertEquals(mockUser.getPassword(), user.getPassword());
        assertEquals(mockUser.getEmail(), user.getEmail());
    }

    @Test
    @Transactional
    public void test_getUserByEmail() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();
        mockUser.setUser_id(userDao.createNewUser(mockUser, false, roleDao));
        User user = userDao.getUserByEmail(mockUser.getEmail());

        assertEquals(mockUser.getUser_id(), user.getUser_id());
        assertEquals(mockUser.getUsername(), user.getUsername());
        assertEquals(mockUser.getPassword(), user.getPassword());
        assertEquals(mockUser.getEmail(), user.getEmail());
    }

}
