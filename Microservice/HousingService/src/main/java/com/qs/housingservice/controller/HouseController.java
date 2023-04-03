//package com.qs.housingservice.controller;
//
//import com.qs.housingservice.domain.House;
//import com.qs.housingservice.domain.Landlord;
//import com.qs.housingservice.service.HouseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("house")
//public class HouseController {
//    private final HouseService houseService;
//
//    @Autowired
//    public HouseController(HouseService houseService) {
//        this.houseService = houseService;
//    }
//
//    @GetMapping("/address")
//    public List<House> getAddress() {return houseService.getAddress();}
//
//    @GetMapping("/name")
//    public List<Landlord> getName() {
//        return houseService.getName();
//    }
//}
