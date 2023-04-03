package com.qs.security.domain.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HRUser {
    private String username;

    private String email;

    private String password;

    private String authToken;
}
