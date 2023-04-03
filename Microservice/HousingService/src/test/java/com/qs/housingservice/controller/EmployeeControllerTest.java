package com.qs.housingservice.controller;


import com.example.housingservice.domain.*;
import com.qs.housingservice.domain.FacilityReportDetail;
import com.qs.housingservice.domain.House;
import com.qs.housingservice.domain.Landlord;
import com.qs.housingservice.domain.request.UpdateComment;
import com.qs.housingservice.service.FacilityReportDetailService;
import com.qs.housingservice.service.FacilityReportService;
import com.qs.housingservice.service.HouseService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    private FacilityReportDetailService facilityReportDetailService;

    @Mock
    private FacilityReportService facilityReportService;

    @Mock
    private HouseService houseService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void testGetAllComments() {
        List<FacilityReportDetail> comments = Arrays.asList(
                new FacilityReportDetail(1, 1, "Comment 1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())),
                new FacilityReportDetail(2, 2, "Comment 2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()))
        );

        Mockito.when(facilityReportService.getAllComments()).thenReturn(comments);

        List<FacilityReportDetail> result = employeeController.getAllComments();

        Assert.assertEquals(comments.size(), result.size());
        Assert.assertEquals(comments.get(0).getComment(), result.get(0).getComment());
        Assert.assertEquals(comments.get(1).getComment(), result.get(1).getComment());
    }



    @Test
    public void testGetAddress() {
        List<House> houses = Arrays.asList(
                new House(1, "Address 1", new Landlord(1, "Landlord 1")),
                new House(2, "Address 2", new Landlord(2, "Landlord 2"))
        );

        Mockito.when(houseService.getAddress()).thenReturn(houses);

        List<House> result = employeeController.getAddress();

        Assert.assertEquals(houses.size(), result.size());
        Assert.assertEquals(houses.get(0).getAddress(), result.get(0).getAddress());
        Assert.assertEquals(houses.get(1).getAddress(), result.get(1).getAddress());
    }

    @Test
    public void testUpdateCommentsByEmpId() {
        UpdateComment updateComment = new UpdateComment(1, "New comment");

        Mockito.doNothing().when(facilityReportDetailService).updateCommentsByEmpId(updateComment.getId(), updateComment.getComment());

        UpdateComment result = employeeController.updateCommentsByEmpId(updateComment);

        Assert.assertEquals(updateComment.getId(), result.getId());
        Assert.assertEquals(updateComment.getComment(), result.getComment());
    }


}