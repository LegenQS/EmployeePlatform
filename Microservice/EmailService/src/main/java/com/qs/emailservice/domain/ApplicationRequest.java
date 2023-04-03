package com.qs.emailservice.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequest {
    private String username;
    private String email;
    private String msg;
}
