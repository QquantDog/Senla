package com.senla.config.test;

import com.senla.config.dev.SpringConfig;
import com.senla.config.dev.WebConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(value = "com.senla", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SpringConfig.class, WebConfig.class}) )
public class TestWebConfig implements WebMvcConfigurer {}
