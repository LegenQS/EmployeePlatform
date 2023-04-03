package com.qs.housingservice.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFacilityReportsResponse {
    private List<FacilityReport> facilityReportList;
}
