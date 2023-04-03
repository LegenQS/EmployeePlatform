package com.qs.housingservice.domain.request;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
public class AddComment {
    private Integer id;
    private String empId;
    private Integer facilityReportId;
    private String comment;
    private Timestamp createDate;
    private Timestamp lastModDate;


}
//    private Integer id;
//
//
//
//    @Column(name = "FacilityReportID")
//    private Integer facilityReportId;
//
//    @Column(name = "EmployeeID")
//    private Integer empId;
//
//    @Column(name = "Comment")
//    private String comment;
//
//    @Column(name = "CreateDate")
//    private Timestamp createDate;
//
//    @Column(name = "LastModificationDate")
//    private Timestamp lastModDate;