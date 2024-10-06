package com.senla.config.dev;

import com.senla.config.test.TestContainerConfig;
import com.senla.config.test.TestWebConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(value = "com.senla", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TestContainerConfig.class, TestWebConfig.class}) )
public class WebConfig implements WebMvcConfigurer {}
