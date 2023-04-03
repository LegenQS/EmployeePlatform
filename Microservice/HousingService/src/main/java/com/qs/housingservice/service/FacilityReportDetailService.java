package com.qs.housingservice.service;

import com.qs.housingservice.dao.FacilityReportDetailDAO;
import com.qs.housingservice.domain.FacilityReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class FacilityReportDetailService {
    private FacilityReportDetailDAO facilityReportDetailDAO;
    @Autowired
    public FacilityReportDetailService(FacilityReportDetailDAO facilityReportDetailDAO) {
        this.facilityReportDetailDAO = facilityReportDetailDAO;
    }

    @Transactional
    public void updateCommentsById(int id, String comment) {
        facilityReportDetailDAO.updateCommentsById(id, comment);
    }

    @Transactional
    public void addComment(FacilityReportDetail facilityReportDetail) {
        facilityReportDetailDAO.addComment(facilityReportDetail);
    }

    @Transactional
    public List<FacilityReportDetail> getReportDetailById(Integer id) {
        return facilityReportDetailDAO.getReportDetailById(id);
    }

    @Transactional
    public List<FacilityReportDetail> getFacilityReportDetailsByReportId(int report_id) {
        return facilityReportDetailDAO.getFacilityReportDetailsByReportId(report_id);
    }
}
