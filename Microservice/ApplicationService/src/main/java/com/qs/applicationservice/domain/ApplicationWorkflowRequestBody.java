package com.qs.applicationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApplicationWorkflowRequestBody {
    private String employee_id;
    private String comment;
}
