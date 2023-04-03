package com.qs.compositeservice.entity.UserService;

import lombok.*;
//import org.springframework.data.annotation.Id;

//@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
//    @Id
    String id;

    String FirstName;
    String LastName;
    Integer CellPhone;
    Integer AlternatePhone;
    String Email;
    String Relationship;
    String Type;
}
