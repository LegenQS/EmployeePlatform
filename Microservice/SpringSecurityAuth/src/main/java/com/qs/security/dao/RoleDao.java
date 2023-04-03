package com.qs.security.dao;

import com.qs.security.entity.Role;
import com.qs.security.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class RoleDao {
    @Autowired
    SessionFactory sessionFactory;

    public Role getRoleByName(String role_name){
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Role r where r.role_name = :role_name";
        Query<Role> query = session.createQuery(hql);
        query.setParameter("role_name", role_name);
        List<Role> roles = query.getResultList();

        return roles == null ? null : roles.get(0);
    }
}
