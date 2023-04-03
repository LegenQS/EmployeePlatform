package com.qs.compositeservice.entity.UserService;

import lombok.*;
//import org.springframework.data.annotation.Id;

//@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonalDocument {
//    @Id
    String id;
    Integer statues;//-1 0 1
    String path;
    String Title;
    String Comment;
    String CreateDate;
}
