package com.qs.compositeservice.entity.HousingService;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "author", "description",  "last_modified_time"})
public class ReportDetailsDTO {
    private String Description;
    private String Author;
    private Timestamp Last_Modified_Time;
}
