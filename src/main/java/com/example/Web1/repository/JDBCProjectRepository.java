package com.example.Web1.repository;

import com.example.Web1.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JDBCProjectRepository implements RowMapper<Project> {
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Project findById(long id) {
        return template.queryForObject("select * from project where id = ?",
                this, id);
    }

    public Project update(Project project) {
        template.update("""
                update project set name = ?,
                description = ?,
                start_date = ?,
                end_date = ?,
                where id = ?
                """,
                project.getName(),
                project.getDescription(),
                project.getEndDate(),
                project.getStartDate(),
                project.getId());
        return findById(project.getId());
    }
    public Project insert(Project project) {
        String sql = """
                insert into project value (
                name = :name,
                description = :description,
                start_date = :starDate,
                end_date = :endDate,
                start_date = :endDate,
                id = :id)
                """;
        namedParameterJdbcTemplate.queryForObject(sql,
                new BeanPropertySqlParameterSource(project),
                Project.class);
        return findById(project.getId());
    }

    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(rs.getLong("id"));
        project.setName(rs.getString("name"));
        project.setDescription(rs.getString("description"));
        project.setStartDate(rs.getDate("start_date"));
        project.setEndDate(rs.getDate("end_date"));
        return project;
    }
}
