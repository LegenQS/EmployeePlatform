package com.qs.security.domain.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserToken {
    private String username;

    private String email;

    private String password;

    private String token;
}
