package com.qs.security.service;

import com.qs.security.dao.TokenDao;
import com.qs.security.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class TokenService {
    private TokenDao tokenDao;

    @Autowired
    public TokenService(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Transactional
    public Token validateToken(String token) {
        return tokenDao.validateToken(token);
    }

    @Transactional
    public Token generateToken(Integer user_id, String email) {
        return tokenDao.generateToken(user_id, email);
    }
}
