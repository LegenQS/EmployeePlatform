package com.qs.userservice.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    @Id
    String id;
    @Indexed(unique = true)
    String userID;
    String FirstName;
    String LastName;
    String MiddleName;
    String PreferedName;
    @Indexed(unique = true)
    String Email;
    Integer CellPhone;
    Integer AlternatePhone;
    String Gender;
    @Indexed(unique = true)
    Integer SSN;
    String DOB;
    String StartDate;
    String EndDate;
    @Indexed(unique = true)
    String DriverLicense;
    String DriverLicenseExpiration;
    String houseId;

    List<Contact> Contacts;
    List<Address> Addresses;
    List<PersonalDocument> personalDocuments;
    List<VisaStatus> visaStatuses;

}
