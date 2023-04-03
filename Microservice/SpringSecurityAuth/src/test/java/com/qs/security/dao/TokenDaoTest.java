package com.qs.security.dao;

import com.qs.security.entity.Token;
import com.qs.security.entity.User;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles(value = "test")
@SpringBootTest
public class TokenDaoTest {
    @Autowired
    TokenDao tokenDao;

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
    public void test_generateToken() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();
        mockUser.setUser_id(userDao.createNewUser(mockUser, false, roleDao));

        String user_email = "1@gmail.com";
        Token fake_token = Token.builder()
                .create_by(mockUser.getUser_id())
                .email(user_email)
                .build();

        Token token = tokenDao.generateToken(mockUser.getUser_id(), user_email);

        assertEquals(token.getCreate_by(), fake_token.getCreate_by());
        assertEquals(token.getEmail(), fake_token.getEmail());
    }

    @Test
    @Transactional
    public void test_validateToken() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();
        mockUser.setUser_id(userDao.createNewUser(mockUser, false, roleDao));

        String user_email = "1@gmail.com";

        Token token = tokenDao.generateToken(mockUser.getUser_id(), user_email);

        Token token_valid = tokenDao.validateToken(token.getToken());

        assertNotNull(token_valid);
    }
}
