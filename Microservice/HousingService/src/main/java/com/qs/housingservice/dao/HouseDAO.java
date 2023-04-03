package com.qs.housingservice.dao;

import com.qs.housingservice.domain.*;
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
public class HouseDAO {

    @Autowired
    SessionFactory sessionFactory;

    public List<House> getAddress() {
        Session session = sessionFactory.getCurrentSession();
        List<House> addressList = null;

        Query query = session.createQuery("select h.address from House h");
        addressList = query.getResultList();

        return addressList;
    }


    public List<Landlord> getName() {
        Session session = sessionFactory.getCurrentSession();
        List<Landlord> name;
        Query query = session.createQuery("select l.firstname, l.cellphone, l.email from Landlord l");
        name = query.getResultList();

        return name;
    }

    public List<House> getAllHouses() {

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<House> criteriaQuery = criteriaBuilder.createQuery(House.class);
        Root<House> root = criteriaQuery.from(House.class);
        criteriaQuery.select(root);


        return session.createQuery(criteriaQuery).getResultList();
    }

    public void addHouse(House house) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(house);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteHouse(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Integer result = null;
        try {
            Query query = session.createQuery("delete from House h where h.id = :id");
            query.setParameter("id", id);
            result = query.executeUpdate();

            System.out.println("Row affected: " + result);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HouseFacility getHouseDetailById(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Facility f where f.houseId = :id");
        query.setParameter("id", id);
        List<Facility> facilities = query.getResultList();

        Query houseQuery = session.createQuery("from House h where h.id=:id");
        houseQuery.setParameter("id", id);
        House house = (House)houseQuery.getResultList().get(0);
        HouseFacility houseFacility = new HouseFacility(house.getId(),house.getLandlordId(),house.getAddress(),house.getMaxOcc(),house.getCurOcc(),facilities);

        return houseFacility;


    }

//    public Integer getNonFullHouseId() {
//        Session session = sessionFactory.getCurrentSession();
//        Integer id = null;
//        Query query = session.createQuery("select h.id from House h where h.curOcc < h.maxOcc order by h.id asc ");
//        id = (Integer) query.getResultList().get(0);
//
//        return id;
//    }

    public Integer getNonFullHouseId() {
        Session session = sessionFactory.getCurrentSession();
        Integer id = -1;
        Query query = session.createQuery("select h.id from House h where h.curOcc < h.maxOcc order by rand()");
        List<Integer> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            id = resultList.get(0);
            House house = session.get(House.class, id);
            house.setCurOcc(house.getCurOcc() + 1);
            session.update(house);
        }
        return id;
    }







}
