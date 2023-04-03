package com.qs.compositeservice.domain.UserService;
import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentInfo {

    String docId;
    String userID;
    Integer statues;//-1 0 1
    String path;
    String Title;
    String Comment;
    String CreateDate;
}