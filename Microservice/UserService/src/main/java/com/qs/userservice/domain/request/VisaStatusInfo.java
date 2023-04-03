package com.qs.userservice.domain.request;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisaStatusInfo {
    String visaId;
    String userID;
    String VisaType;
    String ActiveFlag;
    String StartDate;
    String EndDate;
    String LastModificationDate;
}
