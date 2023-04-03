package com.qs.housingservice.controller;

import com.example.housingservice.domain.*;
import com.qs.housingservice.domain.FacilityReport;
import com.qs.housingservice.domain.FacilityReportDetail;
import com.qs.housingservice.domain.House;
import com.qs.housingservice.domain.HouseFacility;
import com.qs.housingservice.domain.request.UpdateComment;
import com.qs.housingservice.service.FacilityReportDetailService;
import com.qs.housingservice.service.FacilityReportService;
import com.qs.housingservice.service.HouseService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;


public class HRControllerTest {
    @Mock
    private HouseService houseService;
    @Mock
    private FacilityReportService facilityReportService;
    @Mock
    private FacilityReportDetailService facilityReportDetailService;


    @InjectMocks
    private HRController hrController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHouses() {
        List<House> houses = new ArrayList<>();
        House house1 = new House();
        house1.setId(1);
        house1.setAddress("123 Main St");
        house1.setMaxOcc(4);
        house1.setCurOcc(2);
        houses.add(house1);
        House house2 = new House();
        house2.setId(2);
        house2.setAddress("456 Oak Ave");
        house2.setMaxOcc(3);
        house2.setCurOcc(1);
        houses.add(house2);

        when(houseService.getAllHouses()).thenReturn(houses);

        List<House> result = hrController.getAllHouses();

        assertEquals(houses.size(), result.size());
        assertEquals(house1.getId(), result.get(0).getId());
        assertEquals(house1.getAddress(), result.get(0).getAddress());
        assertEquals(house1.getMaxOcc(), result.get(0).getMaxOcc());
        assertEquals(house1.getCurOcc(), result.get(0).getCurOcc());
        assertEquals(house2.getId(), result.get(1).getId());
        assertEquals(house2.getAddress(), result.get(1).getAddress());
        assertEquals(house2.getMaxOcc(), result.get(1).getMaxOcc());
        assertEquals(house2.getCurOcc(), result.get(1).getCurOcc());
    }

    @Test
    public void testDeleteHouse() {
        // Set up the input and expected output
        Integer houseId = 1;
        String expectedOutput = "House Deleted!";

        // Mock the houseService.deleteHouse() method to return void
        doNothing().when(houseService).deleteHouse(houseId);

        // Call the deleteHouse() method
        String actualOutput = hrController.deleteHouse(houseId);

        // Verify that the houseService.deleteHouse() method was called once with the correct input
        verify(houseService, times(1)).deleteHouse(houseId);

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetHouseDetailById() {
        // Arrange
        Integer id = 1;
        HouseFacility expectedHouseFacility = new HouseFacility();
        when(houseService.getHouseDetailById(id)).thenReturn(expectedHouseFacility);

        // Act
        HouseFacility actualHouseFacility = hrController.getHouseDetailById(id);

        // Assert
        assertEquals(expectedHouseFacility, actualHouseFacility);
    }

    @Test
    public void testGetAllReportsByHr() {
        // Arrange
        List<FacilityReport> expectedFacilityReports = new ArrayList<>();
        when(facilityReportService.getAllReportsByHr()).thenReturn(expectedFacilityReports);

        // Act
        List<FacilityReport> actualFacilityReports = hrController.getAllReportsByHr();

        // Assert
        assertEquals(expectedFacilityReports, actualFacilityReports);
    }

    @Test
    public void testGetReportDetailById() {
        // Arrange
        Integer id = 1;
        List<FacilityReportDetail> expectedFacilityReportDetails = new ArrayList<>();
        when(facilityReportDetailService.getReportDetailById(id)).thenReturn(expectedFacilityReportDetails);

        // Act
        List<FacilityReportDetail> actualFacilityReportDetails = hrController.getReportDetailById(id);

        // Assert
        assertEquals(expectedFacilityReportDetails, actualFacilityReportDetails);
    }

    @Test
    public void testUpdateCommentsByEmpId() {
        // Arrange
        UpdateComment updateComment = new UpdateComment();
        updateComment.setId("1");
        updateComment.setComment("Updated comment");

        // Act
        UpdateComment result = hrController.updateCommentsByEmpId(updateComment);

        // Assert
        verify(facilityReportDetailService, times(1)).updateCommentsByEmpId(updateComment.getId(), updateComment.getComment());
        assertEquals(updateComment, result);
    }


//
//    @Test
//    public void testGetNonFullHouseId() {
//        // mock the behavior of the HouseService to return the expected integer
//        Mockito.when(houseService.getNonFullHouseId()).thenReturn(1);
//
//        // call the method being tested
//        Integer result = employeeContr.getNonFullHouseId();
//
//        // verify that the HouseService method was called
//        Mockito.verify(houseService).getNonFullHouseId();
//
//        // verify that the result is the expected integer
//        Assert.assertEquals(Integer.valueOf(1), result);
//    }


}




