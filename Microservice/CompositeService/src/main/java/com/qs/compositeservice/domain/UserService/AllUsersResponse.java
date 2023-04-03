package com.qs.compositeservice.domain.UserService;

import com.qs.compositeservice.entity.UserService.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllUsersResponse {
    private List<User> users;
}
