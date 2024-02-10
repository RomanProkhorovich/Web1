package com.example.Web1.config;

import com.example.Web1.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@AllArgsConstructor
public class ProjectSequence implements BeforeConvertCallback<Project> {
    private final JdbcTemplate template;
    @Override
    public Project onBeforeConvert(Project project)  {
        if (project.getId() == null) {

            Long id = template.query("SELECT nextval('project_seq')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getLong(1);
                        } else {
                            throw new SQLException("Unable to retrieve value from sequence");
                        }
                    });
            project.setId(id);
        }

        return project;
    }
}
