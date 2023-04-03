package com.qs.housingservice.domain.response;

import com.qs.housingservice.dto.ReportDetailsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor

public class ReportResponse {
    private String Title;
    private String Description;
    private String Author;
    private Timestamp created;
    private String Status;
    private List<ReportDetailsDTO> comments;

    public ReportResponse(String title, String description, String author, Timestamp created, String status, List<ReportDetailsDTO> comments) {
        Title = title;
        Description = description;
        Author = author;
        this.created = created;
        Status = status;
        this.comments = comments;
    }
}
