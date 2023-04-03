package com.qs.compositeservice.entity.ApplicationService;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationWorkflow {
    public static final int STATUS_PENDING = 0;
    public static final int STATUS_APPROVED= 1;
    public static final int STATUS_REJECTED = 2;
    private int id;

    private int employee_id;

    private Timestamp create_date;
    private Timestamp last_modification_date;
    private int status;
    private String comment;

    public ApplicationWorkflow(int employee_id, Timestamp create_date, Timestamp last_modification_date, int status, String comment) {
        this.employee_id = employee_id;
        this.create_date = create_date;
        this.last_modification_date = last_modification_date;
        this.status = status;
        this.comment = comment;
    }

    public ApplicationWorkflow(int employee_id, int status, String comment) {
        this.employee_id = employee_id;
        this.status = status;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ApplicationWorkflow{" +
                ", employee_id=" + employee_id +
                ", create_date=" + create_date +
                ", last_modification_date=" + last_modification_date +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                '}';
    }
}
