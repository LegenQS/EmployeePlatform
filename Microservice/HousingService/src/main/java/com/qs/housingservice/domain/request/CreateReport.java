package com.qs.housingservice.domain.request;


import lombok.Data;
import java.sql.Timestamp;

@Data
public class CreateReport {
    private Integer id;
    private Integer facilityId;
    private String empId;
    private String title;
    private String description;
    private Timestamp createDate;
    private String status;


    public CreateReport(int i, int i1, String title, String description) {
    }
}