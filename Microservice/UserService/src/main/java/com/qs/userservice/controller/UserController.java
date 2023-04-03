package com.qs.userservice.controller;

import com.qs.userservice.domain.entity.Address;
import com.qs.userservice.domain.entity.Contact;
import com.qs.userservice.domain.entity.Employee;
import com.qs.userservice.domain.entity.PersonalDocument;
import com.qs.userservice.domain.request.*;
import com.qs.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //get all employees
    @GetMapping(value = "/employee/all")
    public List<Employee> findAllEmployees() {
//        System.out.println("get all employee");
        return userService.getAllEmployee();
    }

    //add new user to database
    @PostMapping("/employee/profile")
    public String addNewEmployee(@RequestBody EmployeeInfo employeeInfo) {
        String userid = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials()) ;
        employeeInfo.setUserID(userid);
        return userService.addNew(employeeInfo);
    }

    //add new address to a user
    @PostMapping("/employee/address")
    public void addAddress(@RequestBody Address address) {
        String user_id = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials()) ;
//        addressInfo.setUserID(userid);
//        System.out.println("add new Address");
        userService.addAddress(address, user_id);
    }

    //add new visa to a user
    @PostMapping("/employee/visa")
    public void addVisa(@RequestBody VisaStatusInfo visaStatusInfo) {
        String userid = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials()) ;
        visaStatusInfo.setUserID(userid);
//        System.out.println("add new visa");

        userService.addVisa(visaStatusInfo);
    }

    //add new contact to a user
    @PostMapping("/employee/contact")
    public void addContact(@RequestBody Contact contact) {
        String user_id = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials()) ;
//        request.setUserID(userid);
//        System.out.println("add new contact");
        userService.addContact(contact, user_id);
    }

    //get employee by its user id
    @GetMapping("/employee")
    public Employee getEmployee() {
        String userid = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());

        return userService.getUserByUserId(userid);
    }

    @GetMapping("/hr/employee")
    public Employee getEmployeeById(@RequestParam String id) {
        return userService.getUserByUserId(id);
    }

    @GetMapping("/employee/document")
    public List<PersonalDocument> getDocument() {
        String userid = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        List<PersonalDocument> res = userService.getDocumentByUserid(userid);

        return res;
    }

    //add new document to a user
    @PostMapping("/employee/document")
    public void addDocument(@RequestBody DocumentInfo documentInfo) {
        String userid = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        documentInfo.setUserID(userid);
        userService.addDocument(documentInfo);
    }

    //modify profile information body
    @PutMapping("/employee/profile")
    public void modifyEmployeeInfo(@RequestBody EmployeeInfo employeeInfo) {
        String userid = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        employeeInfo.setUserID(userid);


        userService.modifyEmployee(employeeInfo);
    }

    //modify address by userid and address id
    @PutMapping("/employee/address")
    public void modifyAddress(@RequestBody Address address) {
        String user_id = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials()) ;
        userService.modifyAddress(address, user_id);
    }

    //modify contact
    @PutMapping("/employee/contact")
    public void modifyContact(@RequestBody Contact contact) {
        String user_id = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials()) ;
        userService.modifyContact(contact, user_id);
    }

    //modify VisaStatus
    @PutMapping("/employee/visa")
    public void modifyVisa(@RequestBody VisaStatusInfo visaStatusInfo) {
        String userid = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials()) ;
        visaStatusInfo.setUserID(userid);
//        System.out.println("modify visa");
        userService.modifyVisa(visaStatusInfo);
    }

    @GetMapping("/hr/{house_id}")
    public List<Employee> getRoommate(@PathVariable String house_id){
        return userService.getEmployeeByHouseId(house_id);
    }

    @GetMapping("/hr/getFullNameById")
    public String getFullNameById(@RequestParam String id){
        return userService.getName(id);
    }

    @GetMapping("/hr/getEmailByEmployeeId")
    public String getEmailByEmployeeId(@RequestParam String employee_id){
        return userService.getEmailByEmployeeId(employee_id);
    }
    @GetMapping("/employee/getFullNameById")
    public String getFullNameByIdEMP(@RequestParam String id){
        return userService.getName(id);
    }

    @GetMapping("/employee/getEmployeeId")
    public String getEmployeeId(){
        String userid = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials()) ;
        return userService.getUserByUserId(userid).getId();
    }

}
