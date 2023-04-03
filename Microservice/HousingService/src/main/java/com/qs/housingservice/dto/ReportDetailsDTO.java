package com.qs.housingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailsDTO {
    private String Description;
    private String Author;
    private Timestamp Last_Modified_Time;
}
