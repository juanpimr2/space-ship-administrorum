package com.aeronauticaimperialis.spaceshipadministrorum.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class FlywayConfig {

    @Bean
    @Profile("!test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://db:5432/mydatabase");
        dataSource.setUsername("myuser");
        dataSource.setPassword("mypassword");
        return dataSource;
    }

    @Bean(initMethod = "migrate")
    @Profile("!test")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(dataSource())
                .locations("classpath:db/migration")
                .load();
    }
}
