package com.example.Web1.repository;

import com.example.Web1.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Repository
public class JDBCTaskRepository implements RowMapper<Task> {
    private final JdbcTemplate template;

    public List<Task> findByProjectId(Long id) {
        return template.query("select * from task where project_id = " + id, this);
    }

    public void batchInsert(List<Task> taskList, Long id) {
        if (taskList == null)
            return;
        taskList.forEach(x -> x.setId(getNextSeqVal()));
        template.batchUpdate("insert into task(id, name, description, final_date, is_ended, project_id)" +
                " values(?, ?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, taskList.get(i).getId());
                ps.setString(2, taskList.get(i).getName());
                ps.setString(3, taskList.get(i).getDescription());
                Date finalDate = taskList.get(i).getFinalDate();
                ps.setDate(4, finalDate == null ? null : new java.sql.Date(finalDate.getTime()));
                Boolean isEnded = taskList.get(i).getIsEnded();
                ps.setBoolean(5, isEnded != null && isEnded.equals(true));
                ps.setLong(6, id);
            }

            @Override
            public int getBatchSize() {
                return taskList.size();
            }
        });
    }

    private Long getNextSeqVal() {
        return template.queryForObject("SELECT nextval('task_seq')", Long.class);
    }

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (!rs.next())
            return null;
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setName(rs.getString("name"));
        task.setDescription(rs.getString("description"));
        task.setFinalDate(rs.getDate("final_date"));
        task.setIsEnded(rs.getBoolean("is_ended"));
        //task.setProject_id(rs.getLong("project_id"));
        return task;
    }
}
