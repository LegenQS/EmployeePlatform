package com.qs.userservice.domain.entity;
import lombok.*;

//@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
//    @Id
    String id;
    String AddressLine1;
    String AddressLine2;
    String City;
    String State;
    Integer ZipCode;
}
