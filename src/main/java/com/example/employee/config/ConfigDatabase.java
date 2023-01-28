package com.example.employee.config;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigDatabase {
    private static final Logger log = LoggerFactory.getLogger(ConfigDatabase.class);
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Employee(101l, "John", "2021-12-01")));
            log.info("Preloading " + repository.save(new Employee(102l, "Alex", "2021-11-23")));
            log.info("Preloading " + repository.save(new Employee(103l, "Ryan", "2021-10-10")));
            log.info("Preloading " + repository.save(new Employee(104l, "Andy", "2021-09-14")));
        };
    }
}
