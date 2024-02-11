package com.example.Web1.controller;

import com.example.Web1.model.Project;
import com.example.Web1.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService service;

    @GetMapping("/all")
    public ResponseEntity<List<Project>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping
    public ResponseEntity<List<Project>> findAllByDates(@RequestParam("start_date")
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                        @RequestParam("end_date")
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(service.findAllByDates(startDate, endDate));
    }
}
