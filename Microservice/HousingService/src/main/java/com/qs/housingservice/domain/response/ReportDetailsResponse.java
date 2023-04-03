package com.qs.housingservice.domain.response;

import com.qs.housingservice.dto.ReportDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDetailsResponse {
    private List<ReportDetailsDTO> detailsDTOS;
}
