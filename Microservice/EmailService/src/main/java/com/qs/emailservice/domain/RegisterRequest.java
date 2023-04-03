package com.qs.emailservice.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String email;
    private String token;
}
