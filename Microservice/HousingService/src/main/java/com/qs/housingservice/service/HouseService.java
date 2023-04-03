package com.qs.housingservice.service;

import com.qs.housingservice.dao.HouseDAO;
import com.qs.housingservice.domain.House;
import com.qs.housingservice.domain.HouseFacility;
import com.qs.housingservice.domain.Landlord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class HouseService {
    private HouseDAO houseDAO;
    @Autowired
    public HouseService(HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    @Transactional
    public List<House> getAddress() {return houseDAO.getAddress();}

    @Transactional
    public List<Landlord> getName() {return houseDAO.getName();}

    @Transactional
    public List<House> getAllHouses() {return houseDAO.getAllHouses();}

    @Transactional
    public void addHouse(House house) {houseDAO.addHouse(house);}

    @Transactional
    public void deleteHouse(Integer id) {houseDAO.deleteHouse(id);}

    @Transactional
    public HouseFacility getHouseDetailById(Integer id) {return houseDAO.getHouseDetailById(id);}

    @Transactional
    public Integer getNonFullHouseId() {return houseDAO.getNonFullHouseId();}
}
