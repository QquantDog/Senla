package com.senla.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.aspect.Wrapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import liquibase.integration.spring.*;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@PropertySource("classpath:/application.properties")
@ComponentScan(basePackages = {"com.senla"})
@EnableAspectJAutoProxy
public class SpringConfig {

    @Autowired
    private Environment env;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }
    @Bean
    public ModelMapper modelMapper(){
        var mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
    @Bean
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db.driver")));
        dataSource.setUrl(Objects.requireNonNull(env.getProperty("db.url")));
        dataSource.setUsername(Objects.requireNonNull(env.getProperty("db.username")));
        dataSource.setPassword(Objects.requireNonNull(env.getProperty("db.password")));

        return dataSource;
    }
    @Bean
    public SpringLiquibase liquibase(){
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(postgresDataSource());
        liquibase.setChangeLog("classpath:/liquibase/changelog-master-v1.xml");
        return liquibase;
    }

}
