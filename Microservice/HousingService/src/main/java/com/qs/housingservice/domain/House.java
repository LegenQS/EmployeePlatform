package com.qs.housingservice.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "house")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "House_ID", unique = true, nullable = false)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Landlord_ID")
//    private Landlord landlord;
    @Column(name = "LandlordID")
    private Integer landlordId;

    @Column(name = "Address")
    private String address;

    @Column(name = "MaxOccupant")
    private Integer maxOcc;

    @Column(name = "CurOccupant")
    private Integer curOcc;

    public House(int i, String s, Landlord landlord) {
    }

    public House(int i, String s, int i1, int i2, int i3, int i4) {
    }

    public House(int i, String address1) {
    }


//    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Facility> houseId = new ArrayList<>();


}
