package com.qs.housingservice.service;

import com.qs.housingservice.dao.FacilityReportDAO;
import com.qs.housingservice.domain.FacilityReport;
import com.qs.housingservice.domain.FacilityReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FacilityReportService {
    private FacilityReportDAO facilityReportDAO;
    @Autowired
    public FacilityReportService(FacilityReportDAO facilityReportDAO) {
        this.facilityReportDAO = facilityReportDAO;
    }

    @Transactional
    public List<FacilityReportDetail> getAllComments() {return facilityReportDAO.getAllComments();}

    @Transactional
    public List<FacilityReport> getAllReports() {return facilityReportDAO.getAllReports();}

    @Transactional
    public void createReportByEmp(FacilityReport facilityReport) {facilityReportDAO.createReportByEmp(facilityReport);}

    @Transactional
    public List<FacilityReport> getAllReportsByHr() {return facilityReportDAO.getAllReportsByHr();}

    @Transactional
    public List<FacilityReport> getReportByEmpId(String empId) {return facilityReportDAO.getReportByEmpId(empId);}



}
