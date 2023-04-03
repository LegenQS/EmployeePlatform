package com.qs.compositeservice.service;

import com.qs.compositeservice.service.remote.RemoteHousingService;
import com.qs.compositeservice.service.remote.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompositeService {

    private RemoteHousingService housingService;
    private RemoteUserService userService;
    private RestTemplate restTemplate;

    @Autowired
    public void setHousingService(RemoteHousingService housingService) {
        this.housingService = housingService;
    }

    @Autowired
    public void setUserService(RemoteUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
