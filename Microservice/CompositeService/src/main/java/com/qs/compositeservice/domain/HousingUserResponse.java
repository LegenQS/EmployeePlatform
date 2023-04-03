package com.qs.compositeservice.domain;

import com.qs.compositeservice.entity.HousingService.House;
import com.qs.compositeservice.entity.UserService.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class HousingUserResponse {
    List<User> users;
    List<House> houses;
}
