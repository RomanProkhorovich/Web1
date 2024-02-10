package com.example.Web1.model;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Long id;
    private String name;
    private String description;
    private Date finalDate;
    private Boolean isEnded;
    private long project_id;
}
