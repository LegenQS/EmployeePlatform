package com.qs.applicationservice.dao;

import com.qs.applicationservice.entity.ApplicationWorkflow;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class ApplicationWorkflowDAO {
    private SessionFactory sessionFactory;
    @Autowired
    public ApplicationWorkflowDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int create(ApplicationWorkflow applicationWorkflow){
        try {
            Session session = sessionFactory.getCurrentSession();
            String employee_id = applicationWorkflow.getEmployee_id();
            ApplicationWorkflow check = getByEmployeeId(employee_id);
            if (check != null){
                return -1;
            }
            return (int)session.save(applicationWorkflow);
        }
        catch (HibernateException e){
            return 0;
        }
    }

    public ApplicationWorkflow getByApplicationWorkflowId(int id){
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationWorkflow> criteriaQuery = criteriaBuilder.createQuery(ApplicationWorkflow.class);
            Root<ApplicationWorkflow> root = criteriaQuery.from(ApplicationWorkflow.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"),id));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ApplicationWorkflow getByEmployeeId(String employee_id){
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationWorkflow> criteriaQuery = criteriaBuilder.createQuery(ApplicationWorkflow.class);
            Root<ApplicationWorkflow> root = criteriaQuery.from(ApplicationWorkflow.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("employee_id"),employee_id));
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
            ApplicationWorkflow applicationWorkflow = session.load(ApplicationWorkflow.class,id);
            session.remove(applicationWorkflow);
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
        }
    }

    public int updateStatus(String employee_id, int status, String comment){
        try{
            Session session = sessionFactory.getCurrentSession();
            String hql = "UPDATE ApplicationWorkflow " +
                    "SET status=:status, last_modification_date=:last_modification_date, comment=:comment " +
                    "WHERE employee_id=:employee_id";
            Query query = session.createQuery(hql);
            query.setParameter("status",status);
            query.setParameter("employee_id",employee_id);
            Date date = new Date();
            query.setParameter("last_modification_date",new Timestamp(date.getTime()));
            query.setParameter("comment",comment);
            int success = query.executeUpdate();
            return success;
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public List<ApplicationWorkflow> getAll(){
        try{
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationWorkflow> criteriaQuery = criteriaBuilder.createQuery(ApplicationWorkflow.class);
            Root<ApplicationWorkflow> root = criteriaQuery.from(ApplicationWorkflow.class);
            criteriaQuery.select(root);
            return session.createQuery(criteriaQuery).getResultList();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<ApplicationWorkflow> getAllPending(){
        try{
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationWorkflow> criteriaQuery = criteriaBuilder.createQuery(ApplicationWorkflow.class);
            Root<ApplicationWorkflow> root = criteriaQuery.from(ApplicationWorkflow.class);
            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("status"),ApplicationWorkflow.STATUS_PENDING));
            return session.createQuery(criteriaQuery).getResultList();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<ApplicationWorkflow> getAllRejected(){
        try{
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationWorkflow> criteriaQuery = criteriaBuilder.createQuery(ApplicationWorkflow.class);
            Root<ApplicationWorkflow> root = criteriaQuery.from(ApplicationWorkflow.class);
            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("status"),ApplicationWorkflow.STATUS_REJECTED));
            return session.createQuery(criteriaQuery).getResultList();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<ApplicationWorkflow> getAllApproved(){
        try{
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationWorkflow> criteriaQuery = criteriaBuilder.createQuery(ApplicationWorkflow.class);
            Root<ApplicationWorkflow> root = criteriaQuery.from(ApplicationWorkflow.class);
            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("status"),ApplicationWorkflow.STATUS_APPROVED));
            return session.createQuery(criteriaQuery).getResultList();
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    public String getApplicationStatus(String employee_id){
        try{
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationWorkflow> criteriaQuery = criteriaBuilder.createQuery(ApplicationWorkflow.class);
            Root<ApplicationWorkflow> root = criteriaQuery.from(ApplicationWorkflow.class);
            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("employee_id"),employee_id));
            ApplicationWorkflow applicationWorkflow = session.createQuery(criteriaQuery).uniqueResult();
            if(applicationWorkflow == null) return "NO APPLICATION";
            if (applicationWorkflow.getStatus() == ApplicationWorkflow.STATUS_APPROVED){
                return "APPROVED";
            }
            else if (applicationWorkflow.getStatus() == ApplicationWorkflow.STATUS_PENDING){
                return "PENDING";
            }
            else{
                return "REJECTED";
            }
        }
        catch (HibernateException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
