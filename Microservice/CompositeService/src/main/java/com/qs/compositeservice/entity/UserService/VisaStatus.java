package com.qs.compositeservice.entity.UserService;

import lombok.*;
//import org.springframework.data.annotation.Id;

//@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisaStatus {
//    @Id
    String id;

    String VisaType;
    String ActiveFlag;
    String StartDate;
    String EndDate;
    String LastModificationDate;
}
