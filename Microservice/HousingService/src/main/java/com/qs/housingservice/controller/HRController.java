package com.qs.housingservice.controller;

import com.qs.housingservice.domain.*;
import com.qs.housingservice.domain.request.*;
import com.qs.housingservice.dto.ReportDTO;
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
@RequestMapping("hr")
public class HRController {
    private HouseService houseService;
    private FacilityReportService facilityReportService;
    private FacilityReportDetailService facilityReportDetailService;
    @Autowired
    public HRController(HouseService houseService, FacilityReportService facilityReportService, FacilityReportDetailService facilityReportDetailService) {
        this.houseService = houseService;
        this.facilityReportService = facilityReportService;
        this.facilityReportDetailService = facilityReportDetailService;
    }

    @GetMapping("/houses")
//    @ApiOperation(value = "Find all houses", response = House.class)
    public List<House> getAllHouses() {return houseService.getAllHouses();}

    @PostMapping("/add/house")
    public String addHouse(@RequestBody AddHouse addHouse) {
        House house = new House();

        house.setLandlordId(addHouse.getLandlordId());
        house.setAddress(addHouse.getAddress());
        house.setMaxOcc(addHouse.getMaxOcc());
        house.setCurOcc(addHouse.getCurOcc());
        houseService.addHouse(house);

        return "House Added!";
    }

    @PostMapping("/delete/house/{id}")
    public String deleteHouse(@PathVariable Integer id) {
        houseService.deleteHouse(id);

        return "House Deleted!";
    }

    @PostMapping("view/house/{id}")
    public HouseFacility getHouseDetailById(@PathVariable Integer id) {
        return houseService.getHouseDetailById(id);
    }

    @GetMapping("view/reports")
    public List<FacilityReport> getAllReportsByHr() {return facilityReportService.getAllReportsByHr();}

    @GetMapping("view/reports/{id}")
    public List<FacilityReportDetail> getReportDetailById(@PathVariable Integer id) {return facilityReportDetailService.getReportDetailById(id);}

    @PostMapping("/update/comment")
    public void updateCommentsById(@RequestBody UpdateComment updateComment) {
        facilityReportDetailService.updateCommentsById(updateComment.getId(), updateComment.getComment());

//        return updateComment;
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
    @GetMapping("/getAllReports")
    public List<ReportDTO>  getAllReportsHR() {
        List<FacilityReport> reports = facilityReportService.getAllReports();
        List<ReportDTO> res = new ArrayList<>();
        for (FacilityReport report : reports) {

            List<FacilityReportDetail> details = facilityReportDetailService
                    .getFacilityReportDetailsByReportId(report.getId());

            List<ReportDetailsDTO> reportDetailsDTOS = new ArrayList<>();
            for (FacilityReportDetail detail : details) {
                ReportDetailsDTO dto = new ReportDetailsDTO(detail.getComment(), detail.getEmpId(), detail.getLastModDate());
                reportDetailsDTOS.add(dto);
            }
            ReportDTO reportDTO = new ReportDTO(report.getTitle(), report.getEmpId(),
                    report.getCreateDate(), report.getStatus(), reportDetailsDTOS);
            res.add(reportDTO);
        }
        return res;
    }




}
