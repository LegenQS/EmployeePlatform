package com.qs.housingservice.dao;

import com.qs.housingservice.domain.FacilityReportDetail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class FacilityReportDetailDAO {
    @Autowired
    SessionFactory sessionFactory;

    public void updateCommentsById(int id, String comment) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        Integer result = null;

        try {
            Query query = session.createQuery("update FacilityReportDetail f set f.comment = :comment, f.lastModDate = now() " +
                    "where f.id = :id");
            query.setParameter("id", id);
//            query.setParameter("empId", empId);
            query.setParameter("comment", comment);

            result = query.executeUpdate();

            System.out.println("Rows affected: " + result);
            System.out.println("comment: " + comment);

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void addComment(FacilityReportDetail facilityReportDetail) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.saveOrUpdate(facilityReportDetail);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<FacilityReportDetail> getReportDetailById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        List<FacilityReportDetail> details = null;

        Query query = session.createQuery("from FacilityReportDetail f where f.id = :id");
        query.setParameter("id", id);
        details = query.getResultList();

        return details;
    }

    public List<FacilityReportDetail> getFacilityReportDetailsByReportId(int report_id){
        try{
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<FacilityReportDetail> criteriaQuery = criteriaBuilder.createQuery(FacilityReportDetail.class);
            Root<FacilityReportDetail> root = criteriaQuery.from(FacilityReportDetail.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("facilityReportId"),report_id));
            return session.createQuery(criteriaQuery).getResultList();
        }
        catch (HibernateException e){
            return null;
        }
    }




}
