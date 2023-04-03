package com.qs.compositeservice.service.remote;

import com.qs.compositeservice.domain.HousingService.AllHousingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("housing-service")
public interface RemoteHousingService {

    @GetMapping("housing-service/housing")
    AllHousingResponse getAllHouses();
}
