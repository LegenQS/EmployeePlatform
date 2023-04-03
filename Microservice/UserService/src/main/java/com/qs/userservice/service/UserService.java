package com.qs.userservice.service;

import com.qs.userservice.domain.entity.*;
import com.qs.userservice.domain.request.*;
import com.qs.userservice.repository.EmplpyeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final EmplpyeeRepo emplpyeeRepo;

    @Autowired
    public UserService(EmplpyeeRepo emplpyeeRepo) {
        this.emplpyeeRepo = emplpyeeRepo;
    }

    public List<Employee> getAllEmployee() {
        return emplpyeeRepo.findAll();
    }

    public String addNew(EmployeeInfo employeeInfo) {
        Employee target = new Employee();
        profileSetter(employeeInfo, target, false);
        target.setVisaStatuses(new ArrayList<>());
        target.setContacts(new ArrayList<>());
        target.setAddresses(new ArrayList<>());
        target.setPersonalDocuments(new ArrayList<>());
        emplpyeeRepo.save(target);

        return target.getId();
    }

    private void profileSetter(EmployeeInfo employeeInfo, Employee target, boolean modify) {
        if (employeeInfo.getFirstName() != null)
            target.setFirstName(employeeInfo.getFirstName());
        if (employeeInfo.getLastName() != null)
            target.setLastName(employeeInfo.getLastName());
        if (employeeInfo.getMiddleName() != null)
            target.setMiddleName(employeeInfo.getMiddleName());
        if (employeeInfo.getPreferedName() != null)
            target.setPreferedName(employeeInfo.getPreferedName());
        if (employeeInfo.getEmail() != null)
            target.setEmail(employeeInfo.getEmail());
        if (employeeInfo.getCellPhone() != null)
            target.setCellPhone(employeeInfo.getCellPhone());
        if (employeeInfo.getAlternatePhone() != null)
            target.setAlternatePhone(employeeInfo.getAlternatePhone());
        if (employeeInfo.getGender() != null)
            target.setGender(employeeInfo.getGender());
        if (employeeInfo.getSSN() != null)
            target.setSSN(employeeInfo.getSSN());
        if (employeeInfo.getDOB() != null)
            target.setDOB(employeeInfo.getDOB());
        if (employeeInfo.getStartDate() != null)
            target.setStartDate(employeeInfo.getStartDate());
        if (employeeInfo.getEndDate() != null)
            target.setEndDate(employeeInfo.getEndDate());
        if (employeeInfo.getDriverLicense() != null)
            target.setDriverLicense(employeeInfo.getDriverLicense());
        if (employeeInfo.getDriverLicenseExpiration() != null)
            target.setDriverLicenseExpiration(employeeInfo.getDriverLicenseExpiration());
        if (employeeInfo.getHouseId() != null && !modify)
            target.setHouseId(employeeInfo.getHouseId());
        if (employeeInfo.getUserID() != null && !modify)
            target.setUserID(employeeInfo.getUserID());
    }

    private void setEmployee(EmployeeInfo employeeInfo, Employee target) {
        profileSetter(employeeInfo, target, true);
        emplpyeeRepo.save(target);
    }

    public List<PersonalDocument> getDocumentByUserid(String id) {
        Employee target = getUserByUserId(id);
        return target.getPersonalDocuments();
    }


    public void addAddress(Address address, String user_id) {
        Employee target = getUserByUserId(user_id);
        target.getAddresses().add(address);

        emplpyeeRepo.save(target);
    }

    public void addDocument(DocumentInfo documentInfo) {
        Employee target = getUserByUserId(documentInfo.getUserID());
        PersonalDocument personalDocument = new PersonalDocument();
        int flag = 0;
//        System.out.println("Start!");
        for (PersonalDocument cur : target.getPersonalDocuments()) {
            if (cur.getPath().equals(documentInfo.getPath())) {
                flag = 1;
                break;
            }
        }
//        System.out.println("ADD!");
        if (flag == 0) {
            personalDocument.setComment(documentInfo.getComment());
            personalDocument.setId(documentInfo.getDocId());
            personalDocument.setPath(documentInfo.getPath());
            personalDocument.setTitle(documentInfo.getTitle());
            personalDocument.setStatues(documentInfo.getStatues());
            personalDocument.setCreateDate(documentInfo.getCreateDate());
            target.getPersonalDocuments().add(personalDocument);
            System.out.println(target);
            emplpyeeRepo.save(target);
        }
    }

    public void modifyAddress(Address address, String user_id) {
        Employee target = getUserByUserId(user_id);
        if (target == null || target.getAddresses() == null)
            return;

        for (Address cur : target.getAddresses()) {
            if (cur.getId().equals(address.getId())) {
                cur.setAddressLine1(address.getAddressLine1());
                cur.setAddressLine2(address.getAddressLine2());
                cur.setCity(address.getCity());
                cur.setState(address.getState());
                cur.setZipCode(address.getZipCode());
                break;
            }
        }
        emplpyeeRepo.save(target);
    }

    public void addContact(Contact contact, String user_id) {
        Employee target = getUserByUserId(user_id);
        target.getContacts().add(contact);

        emplpyeeRepo.save(target);
    }

    public void modifyContact(Contact contact, String user_id) {
        Employee target = getUserByUserId(user_id);
        for (Contact cur : target.getContacts()) {
            if (cur.getId().equals(contact.getId())) {
                cur.setFirstName(contact.getFirstName());
                cur.setLastName(contact.getLastName());
                cur.setCellPhone(contact.getCellPhone());
                cur.setAlternatePhone(contact.getAlternatePhone());
                cur.setEmail(contact.getEmail());
                cur.setRelationship(contact.getRelationship());
                cur.setType(contact.getType());
                break;
            }
        }
        emplpyeeRepo.save(target);
    }

    public void addVisa(VisaStatusInfo visaStatusInfo) {
        Employee target = getUserByUserId(visaStatusInfo.getUserID());
        VisaStatus visaStatus = new VisaStatus();
        visaStatus.setId(visaStatusInfo.getVisaId());
        visaStatus.setVisaType(visaStatusInfo.getVisaType());
        visaStatus.setActiveFlag(visaStatusInfo.getActiveFlag());
        visaStatus.setStartDate(visaStatusInfo.getStartDate());
        visaStatus.setEndDate(visaStatusInfo.getEndDate());
        visaStatus.setLastModificationDate(visaStatusInfo.getLastModificationDate());
        target.getVisaStatuses().add(visaStatus);

        emplpyeeRepo.save(target);
    }

    public void modifyVisa(VisaStatusInfo visaStatusInfo) {
        Employee target = getUserByUserId(visaStatusInfo.getUserID());
        for (VisaStatus cur : target.getVisaStatuses()) {
            if (cur.getId().equals(visaStatusInfo.getVisaId())) {
                cur.setVisaType(visaStatusInfo.getVisaType());
                cur.setActiveFlag(visaStatusInfo.getActiveFlag());
                cur.setStartDate(visaStatusInfo.getStartDate());
                cur.setEndDate(visaStatusInfo.getEndDate());
                cur.setLastModificationDate(visaStatusInfo.getLastModificationDate());
                break;
            }
        }
        emplpyeeRepo.save(target);
    }

    public void modifyEmployee(EmployeeInfo employeeInfo) {
        Employee target = getUserByUserId(employeeInfo.getUserID());
        setEmployee(employeeInfo, target);
    }

    public Employee getUserByUserId(String id) {
        List<Employee> e = emplpyeeRepo.findEmployeeByUserID(id);
//        System.out.println("User ID size " + e.size() + " : " + e.isEmpty());
        if (e == null || e.isEmpty()) return null;
        return e.get(0);
    }

    public List<Employee> getEmployeeByHouseId(String id) {
        return emplpyeeRepo.findEmployeeByHouseId(id);
    }

    public String getName(String id) {
        List<Employee> employees = emplpyeeRepo.findEmployeeById(id);
        if (employees.isEmpty())
            return "null";

        Employee employee = employees.get(0);
        String firstName = employee.getFirstName();
        String middleName = employee.getMiddleName();
        String lastName = employee.getLastName();
        StringBuilder fullName = new StringBuilder(firstName);

        if (middleName != null && !middleName.equals(""))
            fullName.append(" ").append(middleName);

        fullName.append(lastName);
//        System.out.println(fullName);
        return fullName.toString();
    }

    public String getEmailByEmployeeId(String employee_id) {
        Employee employee = emplpyeeRepo.findEmployeeById(employee_id).get(0);
        return employee.getEmail();
    }
}
