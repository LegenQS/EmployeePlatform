package com.qs.compositeservice.entity.HousingService;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "title", "description", "author", "created", "status", "comments" })

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
