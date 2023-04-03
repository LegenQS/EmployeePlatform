package com.qs.security.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="Token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", unique = true, nullable = false)
    private Integer token_id;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

    @Column(name = "expiration_date")
    private Timestamp expiration_date;

    @Column(name = "create_by")
    private Integer create_by;
}
