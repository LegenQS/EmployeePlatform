package com.qs.housingservice.domain.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateComment {
    private int id;
    private String comment;
    private Timestamp lastModDate;

    public UpdateComment(int i, String newComment) {
    }

    public UpdateComment() {

    }
}
