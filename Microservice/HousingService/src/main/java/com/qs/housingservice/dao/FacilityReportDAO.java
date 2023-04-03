package com.qs.housingservice.dao;

import com.qs.housingservice.domain.FacilityReport;
import com.qs.housingservice.domain.FacilityReportDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class FacilityReportDAO {

    @Autowired
    SessionFactory sessionFactory;

    public List<FacilityReportDetail> getAllComments() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<FacilityReportDetail> criteriaQuery = criteriaBuilder.createQuery(FacilityReportDetail.class);
        Root<FacilityReportDetail> root = criteriaQuery.from(FacilityReportDetail.class);
        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    public List<FacilityReport> getAllReports() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<FacilityReport> criteriaQuery = criteriaBuilder.createQuery(FacilityReport.class);
        Root<FacilityReport> root = criteriaQuery.from(FacilityReport.class);
        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    public void createReportByEmp(FacilityReport facilityReport) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.save(facilityReport);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<FacilityReport> getAllReportsByHr() {

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<FacilityReport> criteriaQuery = criteriaBuilder.createQuery(FacilityReport.class);
        Root<FacilityReport> root = criteriaQuery.from(FacilityReport.class);
        criteriaQuery.select(root)
                .orderBy(criteriaBuilder.desc(root.get("createDate")));

        return session.createQuery(criteriaQuery).getResultList();

    }

    public List<FacilityReport> getReportByEmpId(String empId) {
        Session session = sessionFactory.getCurrentSession();
        List<FacilityReport> report = null;

        Query query = session.createQuery("from FacilityReport f where f.empId = :empId");
        query.setParameter("empId", empId);
        report = query.getResultList();

        return report;
    }


    public void setSessionFactory(SessionFactory sessionFactory) {
    }
}
