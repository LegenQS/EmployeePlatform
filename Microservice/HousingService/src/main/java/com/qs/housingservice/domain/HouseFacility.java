package com.qs.housingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class HouseFacility {
    private int house_id;
    private int landlord_id;
    private String address;
    private int maxOccupant;
    private int curOccupant;
    private List<Facility> facilityList;
}
