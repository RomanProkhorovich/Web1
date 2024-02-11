package com.example.Web1.service;

import com.example.Web1.exception.EntityNotFoundException;
import com.example.Web1.model.Project;
import com.example.Web1.repository.JDBCProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private JDBCProjectRepository repository;

    public Project create(Project project){
        try {
            return repository.insert(project);
        }
        catch (DataAccessException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    public Project update(Project project){
        try {
            return repository.update(project);
        }
        catch (DataAccessException ex){
            throw new EntityNotFoundException(ex.getMessage());
        }
    }
    public Project findById(Long id){
        try {
            return repository.findById(id);
        }
        catch (DataAccessException ex){
            throw new EntityNotFoundException(ex.getMessage());
        }
    }
    public List<Project> findAll(){
        return repository.findAll();
    }
    public List<Project> findAllByDates(Date startDate, Date endDate){
        return repository.findAllByDates(startDate, endDate);
    }
}
