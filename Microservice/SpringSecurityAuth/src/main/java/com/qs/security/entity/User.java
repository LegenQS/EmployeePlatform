package com.qs.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="User")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer user_id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_date")
    private Timestamp create_date;

    @Column(name = "last_modification_date")
    private Timestamp last_modification_date;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Role",
        joinColumns = {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="role_id")})
    @ToString.Exclude
    @JsonIgnore
    private List<Role> roles;
}
