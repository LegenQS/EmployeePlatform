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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class UserDao {
    @Autowired
    SessionFactory sessionFactory;

    public User loadUserByUsername(String username){
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User u where u.username = :username or u.email = :username";
        Query<User> query = session.createQuery(hql);
        query.setParameter("username", username);
        List<User> users = query.getResultList();

        return users == null ? null : users.get(0);
    }

    public User getUserByName(String username) {
        Session session;
        Optional<User> user = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("username"), username);

            cq.select(root).where(predicate);
            user = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return (user == null || !user.isPresent()) ? null : user.get();
    }

    public User getUserByEmail(String email) {
        Session session;
        Optional<User> user = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("email"), email);

            cq.select(root).where(predicate);
            user = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return (user == null || !user.isPresent()) ? null : user.get();
    }

    public User getUserByID(Integer user_id) {
        Session session;
        Optional<User> user = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("user_id"), user_id);

            cq.select(root).where(predicate);
            user = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return (user == null || !user.isPresent()) ? null : user.get();
    }

    public void updateUserStatus(Integer user_id) {
        Session session = sessionFactory.getCurrentSession();
        User user = getUserByID(user_id);
        user.setActive(true);
        session.saveOrUpdate(user);
    }

    public int createNewUser(User user, boolean is_admin, RoleDao roleDao) {
        Session session;
        Integer seq = 0;

        Role hr_role = roleDao.getRoleByName("HR");
        Role employee_role = roleDao.getRoleByName("employee");

        List<Role> roles = new ArrayList<>();

        if (is_admin)
            roles.add(hr_role);
        else roles.add(employee_role);

        user.setRoles(roles);
        user.setCreate_date(new Timestamp(System.currentTimeMillis()));
        user.setLast_modification_date(user.getCreate_date());
        user.setActive(!is_admin);

        try{
            session = sessionFactory.getCurrentSession();
            seq = (Integer) session.save(user);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return seq;
    }

}
