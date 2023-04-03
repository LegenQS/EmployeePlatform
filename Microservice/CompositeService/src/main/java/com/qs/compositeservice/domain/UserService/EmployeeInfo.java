package com.qs.compositeservice.domain.UserService;

import lombok.*;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeInfo {

    String userID;
    String FirstName;
    String LastName;
    String MiddleName;
    String PreferedName;
    String Email;
    Integer CellPhone;
    Integer AlternatePhone;
    String Gender;
    Integer SSN;
    String DOB;
    String StartDate;
    String EndDate;
    String DriverLicense;
    String DriverLicenseExpiration;
    String houseId;
}
