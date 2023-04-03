package com.qs.housingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ReportDTO {
    private String title;
    private String createdBy;
    private Timestamp createdTime;
    private String Status;
    private List<ReportDetailsDTO> comments;
}
