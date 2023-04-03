package com.qs.compositeservice.entity.ApplicationService;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DigitalDocument {

    private int id;
    private String type;
    private int isRequired;
    private String path;
    private String description;
    private String title;

}
