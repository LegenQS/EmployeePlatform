package com.qs.housingservice.controller;

import com.qs.housingservice.domain.*;
import com.qs.housingservice.domain.request.*;
import com.qs.housingservice.domain.response.*;
import com.qs.housingservice.dto.ReportDetailsDTO;
import com.qs.housingservice.service.FacilityReportDetailService;
import com.qs.housingservice.service.FacilityReportService;
import com.qs.housingservice.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("employee")
public class EmployeeController {
    private FacilityReportDetailService facilityReportDetailService;
    private FacilityReportService facilityReportService;
    private HouseService houseService;

    @Autowired
    public EmployeeController(FacilityReportDetailService facilityReportDetailService, FacilityReportService facilityReport, HouseService houseService) {
        this.facilityReportDetailService = facilityReportDetailService;
        this.facilityReportService = facilityReport;
        this.houseService = houseService;
    }

    @GetMapping("/reports")
    public UserFacilityReportsResponse getAllReports() {
        return new UserFacilityReportsResponse(facilityReportService.getAllReports());
    }

    @GetMapping("/address")
    public List<House> getAddress() {
        return houseService.getAddress();
    }

    @PostMapping("/update/comment")
    public String updateCommentsById(@RequestParam int comment_id,
                                     @RequestParam String employee_id,
                                     @RequestParam String comment) {

        List<FacilityReportDetail> facilityReportDetails = facilityReportDetailService.getReportDetailById(comment_id);
        if (facilityReportDetails == null || facilityReportDetails.isEmpty())
            return "Comment_id does not exists";
        else {
            if (facilityReportDetails.get(0).getEmpId().equals(employee_id)) {
                facilityReportDetailService.updateCommentsById(comment_id,comment);
                return "successfully updated";
            }
            else {
                return "CANNOT UPDATE OTHER USER'S COMMENT!";
            }
        }
    }

    @PostMapping("/create/report")
    public String createReportByEmp(@RequestBody FacilityReport facilityReport) {
        facilityReport.setCreateDate(new Timestamp(System.currentTimeMillis()));
        facilityReport.setStatus("Open");

        facilityReportService.createReportByEmp(facilityReport);

        return "Report Created!";
    }

    @PostMapping("/create/comment")
    public String addComment(@RequestBody AddComment addComment) {
        FacilityReportDetail facilityReportDetail = new FacilityReportDetail();

        facilityReportDetail.setFacilityReportId(addComment.getFacilityReportId());
        facilityReportDetail.setEmpId(addComment.getEmpId());
        facilityReportDetail.setComment(addComment.getComment());
        facilityReportDetail.setCreateDate(new Timestamp(System.currentTimeMillis()));
        facilityReportDetail.setLastModDate(new Timestamp(System.currentTimeMillis()));

        facilityReportDetailService.addComment(facilityReportDetail);

        return "Comment Created!";
    }

    @GetMapping("/reports/employee/{empId}")
    public List<FacilityReport> getReportByEmpId(@PathVariable String empId) {
        return facilityReportService.getReportByEmpId(empId);
    }

    @GetMapping("/comments/DTO")
    public ReportResponseList getReportDetailsDTO(@RequestParam String employee_id) {
        List<FacilityReport> reports = facilityReportService.getReportByEmpId(employee_id);
        List<ReportResponse> responses = new ArrayList<>();
        for (FacilityReport report : reports) {
            List<FacilityReportDetail> details = facilityReportDetailService
                    .getFacilityReportDetailsByReportId(report.getId());
            List<ReportDetailsDTO> dtos = new ArrayList<>();
            for (FacilityReportDetail detail : details) {
                ReportDetailsDTO dto = new ReportDetailsDTO(detail.getComment(),
                        detail.getEmpId(), detail.getLastModDate());
                dtos.add(dto);
            }
            ReportResponse reportResponse = new ReportResponse(report.getTitle()
                    , report.getDescription()
                    , report.getEmpId()
                    , report.getCreateDate()
                    , report.getStatus()
                    , dtos);
            responses.add(reportResponse);
        }
        return new ReportResponseList(responses);
    }

    @GetMapping("/get/houseId")
    public Integer getNonFullHouseId() {
        return houseService.getNonFullHouseId();
    }
}
