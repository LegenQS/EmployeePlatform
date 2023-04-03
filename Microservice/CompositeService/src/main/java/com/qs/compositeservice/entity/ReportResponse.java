package com.qs.compositeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private String Title;
    private String Description;
    private String Author;
    private Timestamp created;
    private String Status;
//    private List<ReportDetailsDTO> details;
}
