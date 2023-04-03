package com.qs.security.dao;

import com.qs.security.entity.Token;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TokenDao {
    @Autowired
    SessionFactory sessionFactory;

    private final int token_length = 50;

    public Token validateToken(String token){
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Token t where t.token = :token";
        Query<Token> query = session.createQuery(hql);
        query.setParameter("token", token);
        List<Token> tokens = query.getResultList();

        return tokens == null || tokens.isEmpty() ? null : tokens.get(0);
    }

    public Token generateToken(Integer user_id, String email){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        time.setMinutes(time.getMinutes() + 30);

        Token token = Token.builder()
                .token(getRandomToken())
                .email(email)
                .create_by(user_id)
                .expiration_date(time)
                .build();

        Session session = sessionFactory.getCurrentSession();
        Integer token_id = (Integer) session.save(token);

        token.setToken_id(token_id);

        return token;
    }

    private String getRandomToken() {
        return RandomStringUtils.randomAlphanumeric(token_length);
    }
}
