package com.qs.security.service;

import com.qs.security.dao.RoleDao;
import com.qs.security.dao.UserDao;
import com.qs.security.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
public class UserServiceTest {
    @InjectMocks    // where the "fake" object will be used
    UserService userService;

    @Mock
    UserDao userDao;

    @Mock
    RoleDao roleDao;

    @Test
    public void test_loadUserByName() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();

        when(userDao.loadUserByUsername(mockUser.getUsername())).thenReturn(mockUser);

        UserDetails userDetails = userService.loadUserByUsername(mockUser.getUsername());

        assertEquals(userDetails.getUsername(), mockUser.getUsername());
    }

    @Test
    public void test_getUserByName() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();

        when(userDao.getUserByName(mockUser.getUsername())).thenReturn(mockUser);

        User user = userService.getUserByName(mockUser.getUsername());
        assertEquals(user.getUsername(), mockUser.getUsername());
        assertEquals(user.getEmail(), mockUser.getEmail());
        assertEquals(user.getPassword(), mockUser.getPassword());
    }

    @Test
    public void test_getUserByEmail() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();

        when(userDao.getUserByEmail(mockUser.getEmail())).thenReturn(mockUser);

        User user = userService.getUserByEmail(mockUser.getEmail());
        assertEquals(user.getUsername(), mockUser.getUsername());
        assertEquals(user.getEmail(), mockUser.getEmail());
        assertEquals(user.getPassword(), mockUser.getPassword());
    }

    @Test
    public void test_createNewUser() {
        User mockUser = User.builder()
                .user_id(1)
                .username("admin")
                .email("1@gmail.com")
                .password("admin")
                .build();

        when(userDao.createNewUser(mockUser, false, roleDao)).thenReturn(mockUser.getUser_id());

        Integer id = userService.createNewUser(mockUser, false);
        assertEquals(id, mockUser.getUser_id());
    }
}
