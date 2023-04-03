package com.qs.compositeservice.controller;

import com.qs.compositeservice.domain.UserService.DocumentInfo;
import com.qs.compositeservice.domain.UserService.EmployeeInfo;
import com.qs.compositeservice.entity.ApplicationService.ApplicationWorkflowRequestBody;
import com.qs.compositeservice.entity.AuthenticationService.Token;
import com.qs.compositeservice.entity.HousingService.ReportDetailsDTO;
import com.qs.compositeservice.entity.HousingService.ReportResponse;
import com.qs.compositeservice.entity.UserService.Employee;
import com.qs.compositeservice.entity.HousingService.ReportResponseList;
import com.qs.compositeservice.service.ApplicationService;
import com.qs.compositeservice.service.CompositeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompositeController {
    private final RestTemplate restTemplate;

    private CompositeService compositeService;

    private final ApplicationService applicationService;

    @Autowired
    public CompositeController(RestTemplate restTemplate, ApplicationService applicationService,
                               CompositeService compositeService) {
        this.restTemplate = restTemplate;
        this.applicationService = applicationService;
        this.compositeService = compositeService;
    }

    private static Map<String, String> prevFile = new HashMap<String, String>(){{
        put("OPT_Receipt", "*");
        put("OPT_EAD", "OPT_Receipt");
        put("I-983", "OPT_EAD");
        put("I-20", "I-983");
        put("OPT_STEM_Receipt", "I-20");
        put("OPT_STEM_EAD", "OPT_STEM_Receipt");
    }};

    @GetMapping("/employee/getReport")
    public ReportResponseList getReport(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String get_apiUrl_employee_id = "http://user-service/user-service/user/employee";
        ResponseEntity<Employee> employeeResponseEntity = restTemplate.exchange(
                get_apiUrl_employee_id, HttpMethod.GET, entity, Employee.class
        );
        String employeeId = employeeResponseEntity.getBody().getId();
//        System.out.println(employeeId);
        ResponseEntity<ReportResponseList> response = restTemplate.exchange(
                "http://housing-service/housing-service/employee/comments/DTO?employee_id="+employeeId,
                HttpMethod.GET, entity, ReportResponseList.class
        );
        ReportResponseList reportResponseList = response.getBody();
        List<ReportResponse> reports = reportResponseList.getReports();
        HashMap<String, String > nameMap = new HashMap<>();
        for (ReportResponse report:reports){
            if (nameMap.getOrDefault(report.getAuthor(),null) == null){
                String fullName = getFullNameByEmployeeIdEmp(request, report.getAuthor());
                nameMap.put(report.getAuthor(),fullName);
            }
            report.setAuthor(nameMap.get(report.getAuthor()));
            for (ReportDetailsDTO dto : report.getComments()){
                if (nameMap.getOrDefault(dto.getAuthor(),null) == null){
                    String fullName =getFullNameByEmployeeIdEmp(request, dto.getAuthor());
                    nameMap.put(dto.getAuthor(),fullName);
                }
                dto.setAuthor(nameMap.get(dto.getAuthor()));
            }
        }
        return reportResponseList;
//        return housingService.getReportDetails();
    }

    @GetMapping("/hr/register")
    public String registerUser(HttpServletRequest request, @RequestParam String email){
        System.out.println(request.getHeader("Authorization"));
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));

        HttpEntity<String> entity = new HttpEntity<>(email, headers);
        ResponseEntity<Token> tokenResponseEntity = restTemplate.exchange(
                "http://auth-service/auth-service/hr/register?email="+email,
                HttpMethod.GET, entity, Token.class
        );

        Token token = tokenResponseEntity.getBody();

        HttpEntity<String> entity1 = new HttpEntity<>(email, headers);
        restTemplate.exchange(
                "http://email-service/email-service/hr/register?email={email}&token={token}",
                HttpMethod.GET, entity1, String.class, email, token.getToken()
        );

        return "added";
    }

    public String getFullNameByEmployeeId(HttpServletRequest request, String id){
        //TODO:TEST WITHOUT HARDCODE JWT TOKEN
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
//        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(id, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://user-service/user-service/user/hr/getFullNameById?id="+id,
                HttpMethod.GET, entity, String.class
        );
        return response.getBody();
    }
    public String getFullNameByEmployeeIdEmp(HttpServletRequest request, String id){
        //TODO:TEST WITHOUT HARDCODE JWT TOKEN
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
//        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(id, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://user-service/user-service/user/employee/getFullNameById?id="+id,
                HttpMethod.GET, entity, String.class
        );
        return response.getBody();
    }

    @GetMapping("/employee/getCurrentApplicationStatus")
    public String getCurrentApplicationStatus(HttpServletRequest request){

        String get_apiUrl_employee_id = "http://user-service/user-service/user/employee";

        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Employee> employeeResponseEntity = restTemplate.exchange(
                get_apiUrl_employee_id, HttpMethod.GET, entity, Employee.class
        );

        String employeeId = employeeResponseEntity.getBody().getId();
        System.out.println(employeeId);
        String get_apiUrl_status =
                "http://application-service/application-service/employee/getApplicationStatus?employee_id="+employeeId;
        ResponseEntity<String> currStatus = restTemplate.exchange(
                get_apiUrl_status, HttpMethod.GET, entity, String.class
        );
        return currStatus.getBody();
    }


    @PostMapping("/employee/createApplication")
    public String createApplication(HttpServletRequest request,
                                    @RequestBody EmployeeInfo employeeInfo,
                                    @RequestParam String comment){
        System.out.println("get in application");
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
        HttpEntity<EmployeeInfo> house_entity = new HttpEntity<>(null, headers);
        String apiUrl = "http://housing-service/housing-service/employee/get/houseId";
        ResponseEntity<Integer> houseIdResponse = restTemplate.exchange(
                apiUrl, HttpMethod.GET, house_entity, Integer.class
        );
        String houseId = String.valueOf(houseIdResponse.getBody());
        employeeInfo.setHouseId(houseId);

        String post_apiUrl = "http://user-service/user-service/user/employee/profile";

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeInfo> requestEntity = new HttpEntity<>(employeeInfo, headers);
        ResponseEntity<String> employee_id = restTemplate.postForEntity(post_apiUrl, requestEntity, String.class);

        String create_app_apiUrl = "http://application-service/application-service/employee/create";
        // Create the RequestBody object
        ApplicationWorkflowRequestBody requestBody = new ApplicationWorkflowRequestBody();
        requestBody.setEmployee_id(employee_id.getBody());
        requestBody.setComment(comment);

        // Create the request headers
        headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HttpEntity with the RequestBody and headers
        HttpEntity<ApplicationWorkflowRequestBody> app_requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the HTTP POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                create_app_apiUrl,
                HttpMethod.POST,
                app_requestEntity,
                String.class);

        // Get the response body
        String responseBody = responseEntity.getBody();

        System.out.println(houseId);
        return "successfully create application: "+ responseBody +"\nCreate employee with id: " + employee_id.getBody();
    }



    @PostMapping("/employee/createEmployee")
    public String createEmployee(HttpServletRequest request, @RequestBody EmployeeInfo employeeInfo){
        System.out.println("get in employee");
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String apiUrl = "http://housing-service/housing-service/employee/get/houseId";
        ResponseEntity<Integer> houseIdResponse = restTemplate.exchange(
                apiUrl, HttpMethod.GET, entity, Integer.class
        );
        String houseId = String.valueOf(houseIdResponse.getBody());
        employeeInfo.setHouseId(houseId);

        String post_apiUrl = "http://user-service/user-service/user/employee/profile";

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeInfo> requestEntity = new HttpEntity<>(employeeInfo, headers);
        ResponseEntity<String> employee_id = restTemplate.postForEntity(post_apiUrl, requestEntity, String.class);

        System.out.println(houseId);
        return "successfully create employee with id: " + employee_id.getBody();
    }
    @PostMapping("/employee/uploadFile")
    public String uploadFile(HttpServletRequest request, @RequestParam MultipartFile file,
//                                       @RequestParam String objectKey,
                                       @RequestParam String title,
                                       @RequestParam String description,
                                       @RequestParam int isRequired) throws IOException {
        String user_id = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        String apiUrl = "http://application-service/application-service/employee/uploadFile";
        HttpHeaders headers = new HttpHeaders();
//        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", file);

        String true_objectKey = createObjKey(user_id, title);
        body.add("file", convertMultipartFile(file));
        body.add("path", true_objectKey);

        body.add("title", title);
        body.add("description", description);
        body.add("isRequired", isRequired);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl,
                HttpMethod.POST, requestEntity, String.class);
        String responseBody = responseEntity.getBody();

        return postPrivateDoc(true_objectKey, title, headers);
    }


    @PostMapping("/employee/visaStatusFileUpload")
    public String visaStatusFileUpload(HttpServletRequest request, @RequestParam MultipartFile file,
//                             @RequestParam String objectKey,
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam int isRequired) throws IOException {
        String user_id = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        //        String ret_Msg = "";
//        String user_id = "1";
        if(!prevFile.containsKey(title)){
            return "Not correct title!";
        }
//        if(!prevFile.get(title).equals("*")){
        if(prevFile.get(title).equals("*")) {
            String curr_file_status = checkStatus(request, createObjKey(user_id, title));
            if(!curr_file_status.equals("EMPTY")){
                if(curr_file_status.contains("APPROVED")){
                    return "Please upload a copy of\n" +
                            "your OPT EAD";
                }else if(curr_file_status.contains("REJECT")){
                    return curr_file_status;
                }else{
                    return "Waiting for HR to approve\n" +
                            "your OPT Receipt";
                }
            }
        }else{
            String prevFileTitle = prevFile.get(title);
            String curr_file_status = checkStatus(request, createObjKey(user_id, title));
            String prev_status = checkStatus(request, createObjKey(user_id, prevFileTitle));
            int status_code = 0;
            if(curr_file_status.equals("EMPTY")){
                if(prev_status.equals("EMPTY")){
                    return "Please upload " + prevFileTitle + " first!";
                }else{
                    if(!prev_status.contains("APPROVED")){
                        if(prev_status.contains("REJECT"))
                            status_code = -1;
                        if(curr_file_status.contains("APPROVED"))
                            status_code = 1;

                        if(prevFileTitle.equals("OPT_EAD")){
                            switch (status_code) {
                                case 1:
                                    return "Please download and fill" +
                                            "out the I-983 form";
                                case 0:  return  "Waiting for HR to approve" +
                                        "your OPT EAD";
                                case -1:  return prevFileTitle + " has been " + prev_status;
                            }

                        }else if(prevFileTitle.equals("I-983")){
                            switch (status_code) {
                                case 1:
                                    return "Please send the I-983" +
                                            "along with all necessary documents to your school and upload the" +
                                            "new I-20";
                                case 0:  return "Waiting for HR to approve" +
                                        "and sign your I-983";
                                case -1:  return prevFileTitle + " has been " + prev_status;
                            }

                        }else if(prevFileTitle.equals("I-20")){
                            switch (status_code) {
                                case 1:
                                    return "Please upload your OPT STEM Receipt";
                                case 0:  return "Waiting for HR to approve" +
                                        "your I-20";
                                case -1:  return prevFileTitle + " has been " + prev_status;
                            }

                        }else if(prevFileTitle.equals("OPT_STEM_Receipt")){
                            switch (status_code) {
                                case 1:
                                    return "Please upload your OPT STEM EAD";
                                case 0:  return "Waiting for HR to approve your OPT STEM Receipt";
                                case -1:  return prevFileTitle + " has been " + prev_status;
                            }

                        }else if(prevFileTitle.equals("OPT_STEM_EAD")){
                            switch (status_code) {
                                case 1:
                                    return "All documents have been approved";
                                case 0:  return "Waiting for HR to approve your OPT STEM EAD";
                                case -1: return prevFileTitle + " has been " + prev_status;
                            }
                        }
                    }
                }
            }else{
                return "Current file existed and the status: " + curr_file_status;
            }
        }


        String apiUrl = "http://application-service/application-service/employee/uploadFile";
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", file);

        String true_objectKey = createObjKey(user_id, title);
        body.add("file", convertMultipartFile(file));
        body.add("path", true_objectKey);

        body.add("title", title);
        body.add("description", description);
        body.add("isRequired", isRequired);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl,
                HttpMethod.POST, requestEntity, String.class);
        String responseBody = responseEntity.getBody();

        return postPrivateDoc(true_objectKey, title, headers);
//        return "Success!";
    }



    private String postPrivateDoc(String path, String title, HttpHeaders headers){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        String apiUrl = "http://user-service/user-service/user/employee/document";
//        HttpHeaders headers = new HttpHeaders();
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        DocumentInfo doc = new DocumentInfo();
        doc.setComment("PENDING");
        doc.setDocId(path);
        doc.setPath(path);
        doc.setTitle(title);
        doc.setStatues(0);
        doc.setCreateDate(formattedDate);
        System.out.println(doc);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DocumentInfo> requestEntity = new HttpEntity<>(doc, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(apiUrl, requestEntity, Void.class);
        return "Success!";
    }

    private String checkStatus(HttpServletRequest request, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-name", request.getHeader("user-name"));
        headers.set("Authorization", request.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String apiUrl = "http://application-service/application-service/employee/getDocStatus?path=" + path;
        ResponseEntity<String> statusResponse = restTemplate.exchange(
                apiUrl, HttpMethod.GET, entity, String.class
        );
        return statusResponse.getBody();
    }

    private String createObjKey(String user_id, String file_type) {
        return user_id + "-" + file_type;
    }

    private File convertToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        return convFile;
    }

    private ByteArrayResource convertMultipartFile(MultipartFile multipartFile) throws IOException {
        byte[] fileContent = multipartFile.getBytes();
        ByteArrayResource resource = new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };
        return resource;
    }


    @PostMapping("/hr/reject")
    public String rejectApplication(HttpServletRequest request,
                                    @RequestParam String employee_id,
                                    @RequestParam(required = false) String comment){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));
        //Employee Service part
        HttpEntity<String> entity = new HttpEntity<>(employee_id, headers);
        ResponseEntity<String> emailResponseEntity = restTemplate.exchange(
                "http://user-service/user-service/user/hr/getEmailByEmployeeId?employee_id="+employee_id,
                HttpMethod.GET, entity, String.class
        );
        String email = emailResponseEntity.getBody();
        //Fullname part
        String fullName = getFullNameByEmployeeId(request,employee_id);

        //Application Service
        String applicationUrl = "http://application-service/hr/application-service/hr/reject?employee_id="+employee_id;
        String cmt = comment;
        if (comment == null) cmt = "NONE";
        applicationUrl+= "&comment=" + cmt;

        ResponseEntity<String> applicationResponseEntity = restTemplate.exchange(applicationUrl,
                HttpMethod.POST, entity, String.class
        );
        String emailUrl = "http://email-service/email-service/application_reject?" +
                "username=" +  fullName+
                "&email=" + email +
                "&msg=" + cmt;
        System.out.println(cmt);
        System.out.println(emailUrl);
        ResponseEntity<String> sendEmailResponseEntity = restTemplate.exchange(
                emailUrl,
                HttpMethod.GET, entity, String.class
        );
        return applicationResponseEntity.getBody();
    }

    @PostMapping("/employee/comment/update")
    public String employeeUpdateComment(HttpServletRequest request,
                                        @RequestParam int comment_id,
                                        @RequestParam String comment){
        String employee_id = getEmployeeId(request);
//        System.out.println(employee_id);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://housing-service/housing-service/employee/update/comment" +
                        "?comment_id=" + comment_id +
                        "&employee_id=" + employee_id +
                        "&comment=" + comment,
                HttpMethod.POST, entity, String.class
        );
        return response.getBody();
    }

    public String getEmployeeId(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://user-service/user-service/user/employee/getEmployeeId",
                HttpMethod.GET, entity, String.class
        );
        return response.getBody();
    }
}
