package com.qs.security.service;

import com.qs.security.dao.RoleDao;
import com.qs.security.dao.TokenDao;
import com.qs.security.dao.UserDao;
import com.qs.security.entity.Token;
import com.qs.security.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
public class TokenServiceTest {
    @InjectMocks    // where the "fake" object will be used
    TokenService tokenService;

    @Mock
    TokenDao tokenDao;

    @Test
    public void test_generateToken() {
        Integer user_id = 1;
        String email =  "1@a.com";

        Token fake_token = Token.builder()
                .create_by(user_id)
                .email(email)
                .token("dwdawdawda")
                .build();

        when(tokenDao.generateToken(user_id, email)).thenReturn(fake_token);

        Token token = tokenService.generateToken(user_id, email);
        assertEquals(token, fake_token);
    }

    @Test
    public void test_validateToken() {
        Integer user_id = 1;
        String email =  "1@a.com";

        Token fake_token = Token.builder()
                .create_by(user_id)
                .email(email)
                .token("dwdawdawda")
                .build();

        when(tokenDao.validateToken(fake_token.getToken())).thenReturn(fake_token);

        Token token = tokenService.validateToken(fake_token.getToken());
        assertEquals(token, fake_token);
    }
}
