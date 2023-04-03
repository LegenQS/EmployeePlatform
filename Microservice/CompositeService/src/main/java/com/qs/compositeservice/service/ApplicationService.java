package com.qs.compositeservice.service;

import com.qs.compositeservice.domain.ApplicationService.ApplicationWorkflowListResponse;
import com.qs.compositeservice.service.remote.RemoteApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApplicationService {
    @Autowired
    private RestTemplate restTemplate;
    private RemoteApplicationService applicationService;
    public ApplicationWorkflowListResponse getAllApplicationWorkflow(){
        ResponseEntity<ApplicationWorkflowListResponse> list = restTemplate.exchange(
                "http://application-service/application-service/application/hr/getAll", HttpMethod.GET, null, ApplicationWorkflowListResponse.class
        );
//        String url = "http://application-service/application-service/hr/getAll";
//        ResponseEntity<ApplicationWorkflowListResponse> response = restTemplate.getForEntity(url, ApplicationWorkflowListResponse.class);
        return  list.getBody();
    }
    public ApplicationWorkflowListResponse getAllApplicationWorkflowFeign(){
        return applicationService.getAllApplication();
    }
}
