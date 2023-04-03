package com.qs.compositeservice.entity.AuthenticationService;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Token {
    private Integer token_id;
    private String token;
    private String email;
    private Timestamp expiration_date;
    private Integer create_by;
}
