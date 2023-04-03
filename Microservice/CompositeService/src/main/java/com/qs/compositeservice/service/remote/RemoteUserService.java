package com.qs.compositeservice.service.remote;

import com.qs.compositeservice.domain.UserService.AllUsersResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user-service")
public interface RemoteUserService {

    @GetMapping("user-service/user")
    AllUsersResponse getAllUsers();
}
