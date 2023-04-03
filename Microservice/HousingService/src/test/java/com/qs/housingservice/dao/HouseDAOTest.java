package com.qs.housingservice.dao;
import com.example.housingservice.domain.*;
import com.qs.housingservice.domain.Facility;
import com.qs.housingservice.domain.House;
import com.qs.housingservice.domain.Landlord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HouseDAOTest {



    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query query;
    @Mock
    private Query<Facility> facilityQuery;

    @Mock
    private Query<House> houseQuery;
    @InjectMocks
    private HouseDAO houseDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);

    }

    @Test
    public void testGetAddress() {
        List<House> addressList = new ArrayList<>();
        addressList.add(new House(1, "123 Main St.", 3, 2, 1, 1));
        addressList.add(new House(2, "456 Second St.", 2, 1, 1, 1));
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("select  h.address from House h")).thenReturn(query);
        when(query.getResultList()).thenReturn(addressList);

        List<House> result = houseDAO.getAddress();

        assertEquals(addressList, result);
        verify(sessionFactory).getCurrentSession();
        verify(session).createQuery("select  h.address from House h");
        verify(query).getResultList();
    }

    @Test
    public void testGetName() {
        List<Object[]> nameList = new ArrayList<>();
        nameList.add(new Object[]{"John", "555-5555"});
        nameList.add(new Object[]{"Jane", "555-1234"});
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("select l.firstname, l.cellphone from Landlord l")).thenReturn(query);
        when(query.getResultList()).thenReturn(nameList);

        List<Landlord> result = houseDAO.getName();

        assertEquals(nameList, result);
        verify(sessionFactory).getCurrentSession();
        verify(session).createQuery("select l.firstname, l.cellphone from Landlord l");
        verify(query).getResultList();
    }

    @Test
    public void testDeleteHouse() {
        Integer id = 1;
        Query query = mock(Query.class);
        when(session.createQuery("delete from House h where h.id = :id")).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        houseDAO.deleteHouse(id);

        verify(session).createQuery("delete from House h where h.id = :id");
        verify(query).setParameter("id", id);
        verify(query).executeUpdate();
    }





}

//    @Test
//    public void testGetAllHouses() {
//        List<House> houseList = new ArrayList<>();
//        houseList.add(new House(1, "123 Main St.", 3, 2, 1, 1));
//        houseList.add(new House(2, "456 Second St.", 2, 1, 1, 1));
//        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
//        CriteriaQuery<House> criteriaQuery = mock(CriteriaQuery.class);
//        Root<House> root = mock(Root.class);
//        when(sessionFactory.getCurrentSession()).thenReturn(session);
//        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
//        when(criteriaBuilder.createQuery(House.class)).thenReturn(criteriaQuery);
//        when(criteriaQuery.from(House.class)).thenReturn(root);
//        when(session.createQuery(criteriaQuery)).thenReturn(query);
//        when(query.getResultList()).thenReturn(houseList);
//
//        List<House> result = houseDAO.getAllHouses();
//
//        assertEquals(houseList, result);
//        verify(sessionFactory).getCurrentSession();
//        verify(session).getCriteriaBuilder();
//        verify(criteriaBuilder).createQuery(House.class);
//        verify(criteriaQuery).from(House.class);
//        verify(session).
