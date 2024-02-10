package com.example.Web1.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

@AllArgsConstructor
@Configuration
public class CreateTables implements CommandLineRunner {
    private final JdbcTemplate template;
/*
    @Bean
    public void createTables() throws SQLException {

    }*/

    public void createTaskTable() throws SQLException {
        String query = """
                create table IF NOT EXISTS task(
                id integer primary key,
                name varchar(255),
                description varchar(510),
                final_date date,
                is_ended boolean,
                project_id integer references project(id)
                );
                CREATE SEQUENCE IF NOT EXISTS "task_seq" START WITH 1;
                """;
        template.execute(query);
    }


    public void createProjectTable() throws SQLException {
        String query = """
                create table IF NOT EXISTS project(
                id integer primary key,
                name varchar(255),
                description varchar(510),
                date_from date,
                date_to date
                );
                
                CREATE SEQUENCE IF NOT EXISTS "project_seq" START WITH 1;
                """;
        template.execute(query);
    }

    @Override
    public void run(String... args) throws Exception {
        createProjectTable();
        createTaskTable();
    }
}
