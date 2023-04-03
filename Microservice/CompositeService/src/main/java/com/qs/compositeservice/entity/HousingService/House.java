package com.qs.compositeservice.entity.HousingService;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class House {
    private String address;
    private String city;
    private String state;
    private String zipCode;
}
