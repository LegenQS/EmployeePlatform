package com.qs.housingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facility")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Facility_ID", unique = true, nullable = false)
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "House_ID")
//    private House house;
    @Column(name = "HouseID")
    private Integer houseId;

    @Column(name = "Type")
    private String type;

    @Column(name = "Description")
    private String description;

    @Column(name = "Quantity")
    private Integer quantity;
    public Facility(int i, String wifi, boolean b) {
    }

//    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FacilityReport> facilityId = new ArrayList<>();
}
