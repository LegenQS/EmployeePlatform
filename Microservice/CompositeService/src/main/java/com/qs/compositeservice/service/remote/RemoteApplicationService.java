package com.qs.compositeservice.service.remote;

import com.qs.compositeservice.domain.ApplicationService.ApplicationWorkflowListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("application-service")
public interface RemoteApplicationService {
    @GetMapping("application-service/application/hr/getAll")
    ApplicationWorkflowListResponse getAllApplication();
}
