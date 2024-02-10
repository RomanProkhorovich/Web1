package com.example.Web1.config;

import com.example.Web1.model.Project;
import com.example.Web1.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@AllArgsConstructor
public class TaskSequence implements BeforeConvertCallback<Task> {
    private final JdbcTemplate template;
    @Override
    public Task onBeforeConvert(Task task) {
        if (task.getId() == null) {

            Long id = template.query("SELECT nextval('task_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getLong(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence");
                        }
                    });
            task.setId(id);
        }

        return task;
    }
}
