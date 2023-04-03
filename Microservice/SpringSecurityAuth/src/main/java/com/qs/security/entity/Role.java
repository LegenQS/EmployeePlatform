package com.qs.security.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="Role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private Integer role_id;

    @Column(name = "role_name")
    private String role_name;

    @Column(name = "role_description")
    private String role_description;

    @Column(name = "create_date")
    private Timestamp create_date;

    @Column(name = "last_modification_date")
    private Timestamp last_modification_date;
}
