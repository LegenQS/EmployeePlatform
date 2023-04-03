package com.qs.applicationservice.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "digital_document")
public class DigitalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "type")
    private String type;
    @Column(name = "isrequired")
    private int isRequired;
    @Column(name = "path")
    private String path;
    @Column(name = "description")
    private String description;
    @Column(name = "title")
    private String title;

}
