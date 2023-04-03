package com.qs.compositeservice.domain.HousingService;

import com.qs.compositeservice.entity.HousingService.House;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllHousingResponse {
    private List<House> houseList;
}
