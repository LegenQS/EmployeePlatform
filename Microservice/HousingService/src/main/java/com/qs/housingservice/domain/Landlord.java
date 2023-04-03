package com.qs.housingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "landlord")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Landlord_ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "FirstName")
    private String firstname;

    @Column(name = "LastName")
    private String lastname;

    @Column(name = "Email")
    private String email;

    @Column(name = "CellPhone")
    private String cellphone;

    public Landlord(int i, String s) {
    }

//    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<House> landlordId = new ArrayList<>();

}
