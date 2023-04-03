package com.qs.compositeservice.domain.ApplicationService;


import com.qs.compositeservice.entity.ApplicationService.ApplicationWorkflow;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationWorkflowListResponse {
    private List<ApplicationWorkflow> workflows;
}
