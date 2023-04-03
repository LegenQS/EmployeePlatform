package com.qs.applicationservice.controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.qs.applicationservice.domain.ApplicationWorkflowRequestBody;
import com.qs.applicationservice.domain.response.ApplicationWorkflowListResponse;
import com.qs.applicationservice.entity.ApplicationWorkflow;
import com.qs.applicationservice.entity.DigitalDocument;
import com.qs.applicationservice.service.ApplicationService;
//import org.bouncycastle.util.Times;
import com.qs.applicationservice.service.DigitalDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
public class ApplicationServiceController {
    private ApplicationService applicationWorkflowService;
    private DigitalDocumentService digitalDocumentService;
    private BasicAWSCredentials basicAWSCredentials;
    private String bucketName = "bfemployeedocs";
    private AmazonS3 amazonS3Client;

    @Autowired
    public ApplicationServiceController(ApplicationService applicationWorkflowService,
                                        DigitalDocumentService digitalDocumentService,
                                        BasicAWSCredentials basicAWSCredentials) {
        this.applicationWorkflowService = applicationWorkflowService;
        this.digitalDocumentService = digitalDocumentService;
        this.basicAWSCredentials = basicAWSCredentials;
        this.amazonS3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(Regions.US_WEST_1)
                .build();
    }

    @PostMapping("/employee/create")
    public String create(@RequestBody ApplicationWorkflowRequestBody requestBody) {
        Date date = new Date();
        Timestamp currTime = new Timestamp(date.getTime());
        ApplicationWorkflow applicationWorkflow = ApplicationWorkflow.builder()
                .employee_id(requestBody.getEmployee_id())
                .status(ApplicationWorkflow.STATUS_PENDING)
                .comment(requestBody.getComment())
                .last_modification_date(currTime)
                .create_date(currTime)
                .build();
        int id = applicationWorkflowService.create(applicationWorkflow);
        if (id == -1) return "duplicate application.";
        return "success, id : " + id;
    }

    @GetMapping("/employee/get")
    public ApplicationWorkflow get(HttpServletRequest request,@RequestParam String id) {
        return applicationWorkflowService.getByEmployeeId(id);
    }

    @PostMapping("/employee/uploadFile")
    public String uploadFile(@RequestParam MultipartFile file,
                             @RequestParam String path,
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam int isRequired) throws IOException {

        String fileName = file.getOriginalFilename();
        ClassLoader classLoader = getClass().getClassLoader();
        File targetFile = new File(classLoader.getResource(".").getFile() + File.separator + fileName);
        file.transferTo(targetFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, targetFile);
//        AmazonS3 amazonS3Client = amazonS3();
        amazonS3Client.putObject(putObjectRequest);
        DigitalDocument check = digitalDocumentService.getByPath(path);
        if (check == null){
            DigitalDocument digitalDocument = DigitalDocument.builder()
                    .path(path)
                    .type(file.getContentType())
                    .title(title)
                    .description(description)
                    .isRequired(isRequired)
                    .build();
            digitalDocumentService.create(digitalDocument);
        }
        return "successful";
    }

    @DeleteMapping("/employee/deleteFile")
    public String deleteFile(@RequestParam String path) throws IOException {
//        AmazonS3 amazonS3Client = amazonS3();
        amazonS3Client.deleteObject(bucketName, path);
        digitalDocumentService.deleteByPath(path);

        return "successful";
    }

    @GetMapping("/employee/downloadFile")
    public void downloadFile(HttpServletResponse response, @RequestParam String path) throws IOException {
        DigitalDocument digitalDocument = digitalDocumentService.getByPath(path);
//        AmazonS3 amazonS3Client = amazonS3();
        S3Object s3Object = amazonS3Client.getObject(bucketName, path);
        InputStream inputStream = s3Object.getObjectContent();
        response.setContentType(digitalDocument.getType());
        response.setHeader("Content-Disposition", "attachment;filename=\"" + path + "\"");
        response.setHeader("Cache-Control","no-cache");
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @PostMapping("/hr/approve")
    public String approve(@RequestParam String employee_id, @RequestParam(required = false) String comment) {
        applicationWorkflowService.approve(employee_id,comment == null ? "NONE" : comment);
        return "approved";
    }

    @PostMapping("/hr/reject")
    public String reject(@RequestParam String employee_id, @RequestParam(required = false) String comment) {
        applicationWorkflowService.reject(employee_id,comment == null ? "NONE" : comment);
        return "rejected";
    }

    @DeleteMapping("/hr/delete")
    public String delete(@RequestParam int id) {
        applicationWorkflowService.delete(id);
        return "deleted";
    }

    @GetMapping("/hr/getAll")
    public ApplicationWorkflowListResponse getAll(){
        return new ApplicationWorkflowListResponse(applicationWorkflowService.getAll());
    }
    @GetMapping("/hr/getAllPending")
    public List<ApplicationWorkflow> getAllPending(){
        return applicationWorkflowService.getAllPending();
    }
    @GetMapping("/hr/getAllRejected")
    public List<ApplicationWorkflow> getAllRejected(){
        return applicationWorkflowService.getAllRejected();
    }
    @GetMapping("/hr/getAllApproved")
    public List<ApplicationWorkflow> getAllApproved(){
        return applicationWorkflowService.getAllApproved();
    }


    @PostMapping("/hr/approveDoc")
    public void approveDoc(@RequestParam String path){
        digitalDocumentService.approveDoc(path);
    }
    @PostMapping("/hr/rejectDoc")
    public void rejectDoc(@RequestParam String path, @RequestParam(required = false) String reason){
        digitalDocumentService.rejectDoc(path,reason);
    }
    @GetMapping("/employee/getApplicationStatus")
    public String getApplicationStatus(@RequestParam String employee_id){
        return applicationWorkflowService.getApplicationStatus(employee_id);
    }
    @GetMapping("/employee/getDocStatus")
    public String getDocStatus(@RequestParam String path){
        return digitalDocumentService.getDocStatus(path);
    }
}
