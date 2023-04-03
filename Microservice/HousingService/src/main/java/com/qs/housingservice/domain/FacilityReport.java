package com.qs.housingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "facility_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FacilityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FacilityReport_ID", unique = true, nullable = false)
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "Facility_ID")
//    private Facility facility;

    @Column(name = "FacilityID")
    private Integer facilityId;

    @Column(name = "EmployeeID")
    private String empId;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "CreateDate")
    private Timestamp createDate;

    @Column(name = "Status")
    private String status;

//    @OneToMany(mappedBy = "facilityReport", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FacilityReportDetail> facilityReportId = new ArrayList<>();
}
