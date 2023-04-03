package com.qs.compositeservice.service;

import com.qs.compositeservice.entity.HousingService.ReportDetailsDTO;
import com.qs.compositeservice.entity.HousingService.ReportResponse;
import com.qs.compositeservice.entity.HousingService.ReportResponseList;
import com.qs.compositeservice.service.remote.RemoteHousingService;
import com.qs.compositeservice.service.remote.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class HousingService {

    private RemoteHousingService housingService;
    private RemoteUserService userService;
    private RestTemplate restTemplate;

    @Autowired
    public void setHousingService(RemoteHousingService housingService) {
        this.housingService = housingService;
    }

    @Autowired
    public void setUserService(RemoteUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ReportResponseList getReportDetails(){
        ResponseEntity<ReportResponseList> response = restTemplate.exchange(
                "http://housing-service/housing-service/api/employee/comments/DTO", HttpMethod.GET, null, ReportResponseList.class
        );
        ReportResponseList reportResponseList = response.getBody();
        List<ReportResponse> reports = reportResponseList.getReports();
        HashMap<String, String > nameMap = new HashMap<>();
        for (ReportResponse report:reports){
            if (nameMap.getOrDefault(report.getAuthor(),null) == null){
                String fullName = getFullNameByEmployeeId(report.getAuthor());
                nameMap.put(report.getAuthor(),fullName);
            }
            report.setAuthor(nameMap.get(report.getAuthor()));
            for (ReportDetailsDTO dto : report.getComments()){
                if (nameMap.getOrDefault(dto.getAuthor(),null) == null){
                    String fullName =getFullNameByEmployeeId(dto.getAuthor());
                    nameMap.put(dto.getAuthor(),fullName);
                }
                dto.setAuthor(nameMap.get(dto.getAuthor()));
            }
        }
        return reportResponseList;
    }

    public String getFullNameByEmployeeId(String id){
        //TODO:TEST WITHOUT HARDCODE JWT TOKEN
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJociIsInBlcm1pc3Npb25zIjpbeyJhdXRob3JpdHkiOiJIUiJ9XSwidXNlci1pZCI6MX0.NVNRE0N7_laf8DvMTsDHocCEIRm58yxvOahZMOVyjMg";
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(id, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://user-service/user-service/user/employee/getFullNameById?id="+id, HttpMethod.GET, entity, String.class
        );
        return response.getBody();
    }
//    public ReportResponse

}
