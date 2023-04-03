package com.qs.userservice.domain.entity;
import lombok.*;

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
