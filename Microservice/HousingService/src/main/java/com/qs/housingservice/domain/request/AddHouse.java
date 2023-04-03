package com.qs.housingservice.domain.request;

import lombok.Data;


@Data
public class AddHouse {
    private Integer id;
    private Integer landlordId;
    private String address;
    private Integer maxOcc;
    private Integer curOcc;

}
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "House_ID", unique = true, nullable = false)
//    private Integer id;
//
//
//    @Column(name = "LandlordID")
//    private Integer landlordId;
//
//    @Column(name = "Address")
//    private String address;
//
//    @Column(name = "MaxOccupant")
//    private Integer maxOcc;