package com.qs.housingservice.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.qs.housingservice.domain.FacilityReportDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FacilityReportDetailDAOTest {

    private FacilityReportDetailDAO facilityReportDetailDAO;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Query<FacilityReportDetail> query;

    @Before
    public void setup() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        facilityReportDetailDAO = new FacilityReportDetailDAO();
        facilityReportDetailDAO.sessionFactory = sessionFactory;
    }

//    @Test
//    public void testUpdateCommentsById() {
//        // Mock the query result
//        when(session.createQuery(anyString())).thenReturn(query);
//
//        when(query.setParameter(anyString(), any())).thenReturn(query);
//        when(query.executeUpdate()).thenReturn(1);
//
//        // Call the method under test
//        facilityReportDetailDAO.updateCommentsById("1", "comment");
//
//        // Verify that the query was executed correctly
//        verify(session).createQuery("update FacilityReportDetail f set f.comment = :comment, f.lastModDate = now() " +
//                "where f.id = :id");
//        verify(query).setParameter("id", "1");
//        verify(query).setParameter("comment", "comment");
//        verify(query).executeUpdate();
//    }

    @Test
    public void testAddComment() {
        // Call the method under test
        FacilityReportDetail facilityReportDetail = new FacilityReportDetail();
        facilityReportDetailDAO.addComment(facilityReportDetail);

        // Verify that the object was saved
        verify(session).saveOrUpdate(facilityReportDetail);
    }
    @Test
    public void testGetReportDetailById() {
        // Mock the query result
        List<FacilityReportDetail> resultList = new ArrayList<>();
        FacilityReportDetail detail = new FacilityReportDetail();
        detail.setId(1);
        resultList.add(detail);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.getResultList()).thenReturn(resultList);

        // Call the method under test
        List<FacilityReportDetail> details = facilityReportDetailDAO.getReportDetailById(1);

        // Verify that the query was executed correctly
        verify(session).createQuery("from FacilityReportDetail f where f.id = :id");
        verify(query).setParameter("id", 1);

        // Verify that the correct object was returned
        assertEquals(resultList, details);
    }
}
