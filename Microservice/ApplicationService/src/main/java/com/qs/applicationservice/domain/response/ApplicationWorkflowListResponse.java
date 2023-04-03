package com.qs.applicationservice.domain.response;

import com.qs.applicationservice.entity.ApplicationWorkflow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationWorkflowListResponse {
    private List<ApplicationWorkflow> workflows;
}
