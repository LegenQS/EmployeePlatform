package com.qs.applicationservice.dao;

import com.qs.applicationservice.entity.DigitalDocument;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class DigitalDocumentDAO {
    private SessionFactory sessionFactory;
    @Autowired
    public DigitalDocumentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public int create(DigitalDocument digitalDocument){
        try {
            Session session = sessionFactory.getCurrentSession();
            return (int)session.save(digitalDocument);
        }
        catch (HibernateException e){
            return 0;
        }
    }

    public DigitalDocument get(int id){
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<DigitalDocument> criteriaQuery = criteriaBuilder.createQuery(DigitalDocument.class);
            Root<DigitalDocument> root = criteriaQuery.from(DigitalDocument.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"),id));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void delete(int id){
        try {
            Session session = sessionFactory.getCurrentSession();
            DigitalDocument digitalDocument = session.load(DigitalDocument.class,id);
            session.remove(digitalDocument);
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
        }
    }


    public DigitalDocument getByPath(String path){
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<DigitalDocument> criteriaQuery = criteriaBuilder.createQuery(DigitalDocument.class);
            Root<DigitalDocument> root = criteriaQuery.from(DigitalDocument.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("path"),path));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteByPath(String path){
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<DigitalDocument> criteriaQuery = criteriaBuilder.createQuery(DigitalDocument.class);
            Root<DigitalDocument> root = criteriaQuery.from(DigitalDocument.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("path"),path));
            DigitalDocument digitalDocument = session.createQuery(criteriaQuery).uniqueResult();
            session.remove(digitalDocument);
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
        }
    }
    public void updateStatus(String path, String status){
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "UPDATE DigitalDocument SET description=:description WHERE path=:path";
            Query query = session.createQuery(hql);
            query.setParameter("description",status);
            query.setParameter("path",path);
            query.executeUpdate();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
        }
    }
    public String getDocStatus(String path){
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<DigitalDocument> criteriaQuery = criteriaBuilder.createQuery(DigitalDocument.class);
            Root<DigitalDocument> root = criteriaQuery.from(DigitalDocument.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("path"),path));
            DigitalDocument digitalDocument = session.createQuery(criteriaQuery).uniqueResult();
            if(digitalDocument==null) {
                System.out.println("EMEMEMEMEMEMEMEM: " + path);
                return "EMPTY";
            }

            return digitalDocument.getDescription();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return "";
        }
    }
}
