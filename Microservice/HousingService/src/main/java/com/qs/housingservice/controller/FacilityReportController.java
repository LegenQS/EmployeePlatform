package com.qs.housingservice.controller;

import com.qs.housingservice.domain.FacilityReport;
import com.qs.housingservice.domain.FacilityReportDetail;
import com.qs.housingservice.service.FacilityReportDetailService;
import com.qs.housingservice.service.FacilityReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("report")
public class FacilityReportController {
    private FacilityReportService facilityReportService;

    @Autowired
    public FacilityReportController(FacilityReportService facilityReportService) {
        this.facilityReportService = facilityReportService;
    }
    @GetMapping("/comments")
    public List<FacilityReportDetail> getAllComments() {return facilityReportService.getAllComments();}

    @GetMapping("/reports")
    public List<FacilityReport> getAllReports() {return facilityReportService.getAllReports();}

}
