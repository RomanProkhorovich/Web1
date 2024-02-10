package com.example.Web1.config;

import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DropTable implements DisposableBean {
    private final JdbcTemplate template;
/*
    @PreDestroy
    public void dropTable(){

    }*/

    private void dropTable(String name) {
        String sql = "DROP TABLE" + name + "IF EXISTS";
        template.execute(sql);
    }

    @Override
    public void destroy() throws Exception {
        dropTable("task");
        dropTable("project");
    }
}
