package com.qs.applicationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DigitalDocumentRequest {
    private int isRequired;
    private String description;
    private String title;
}
