package com.qs.compositeservice.entity.ApplicationService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationWorkflowRequestBody {
    private String employee_id;
    private String comment;
}
