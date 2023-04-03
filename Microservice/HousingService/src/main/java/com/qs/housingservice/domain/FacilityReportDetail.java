package com.qs.housingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "facility_report_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FacilityReportDetail_ID", unique = true, nullable = false)
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "FacilityReport_ID")
//    private FacilityReport facilityReport;

    @Column(name = "FacilityReportID")
    private Integer facilityReportId;

    @Column(name = "EmployeeID")
    private String empId;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "CreateDate")
    private Timestamp createDate;

    @Column(name = "LastModificationDate")
    private Timestamp lastModDate;
}
