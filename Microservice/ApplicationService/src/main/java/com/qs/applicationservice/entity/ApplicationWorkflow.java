package com.qs.applicationservice.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "application_workflow")
public class ApplicationWorkflow {
    public static final int STATUS_PENDING = 0;
    public static final int STATUS_APPROVED= 1;
    public static final int STATUS_REJECTED = 2;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "employee_id")
    private String employee_id;
    @Column(name = "create_date")
    private Timestamp create_date;
    @Column(name = "last_modification_date")
    private Timestamp last_modification_date;
    @Column(name = "status")
    private int status;
    @Column(name = "comment")
    private String comment;

    public ApplicationWorkflow(String employee_id, Timestamp create_date, Timestamp last_modification_date, int status, String comment) {
        this.employee_id = employee_id;
        this.create_date = create_date;
        this.last_modification_date = last_modification_date;
        this.status = status;
        this.comment = comment;
    }

    public ApplicationWorkflow(String employee_id, int status, String comment) {
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
