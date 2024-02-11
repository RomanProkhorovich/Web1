package com.example.Web1.repository;

import com.example.Web1.model.Project;
import com.example.Web1.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class JDBCProjectRepository implements RowMapper<Project> {
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JDBCTaskRepository taskRepository;

    public Project findById(long id) {
        Project project = template.queryForObject("select * from project where id = ?",
                this, id);
        List<Task> byProjectId = taskRepository.findByProjectId(id);
        project.setTasks(byProjectId);
        return project;
    }

    public List<Project> findAll() {
        return template.query("select * from project ", this);
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

    public List<Project> findAllByDates(Date startDate, Date endDate) {
        String sql = """
                select * from project
                where
                start_date > ?
                and
                end_date < ?
                """;
        return template.queryForList(sql, Project.class, startDate, endDate);
    }

    public void delete(Long id) {
        String sql = """
                delete from project
                where id = 
                """ + id;
        String deleteTasks = """
                delete from task
                where project_id = 
                """ + id;
        template.execute(deleteTasks + ";" + sql);
    }

    public Project insert(Project project) throws DataAccessException {
        project.setId(getNextSeqVal());
        String sql = """
                insert into project(name, description, start_date, end_date, id) values (
                :name,
                :description,
                :starDate,
                :endDate,
                :id
                )
                """;
        Project res = namedParameterJdbcTemplate.queryForObject(sql,
                new BeanPropertySqlParameterSource(project),
                Project.class);
        taskRepository.batchInsert(project.getTasks());

        return findById(res.getId());
    }


    private Long getNextSeqVal(){
        return template.queryForObject("SELECT nextval('project_seq')", Long.class);
    }


    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(rs.getLong("id"));
        project.setName(rs.getString("name"));
        project.setDescription(rs.getString("description"));
        project.setStartDate(rs.getDate("date_from"));
        project.setEndDate(rs.getDate("date_to"));
        return project;
    }
}
