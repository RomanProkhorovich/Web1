package com.example.Web1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<Task> tasks;
}
