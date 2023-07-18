package com.example.employee.config;

import com.example.employee.entity.Employee;
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
            log.info("Preloading " + repository.save(Employee.builder().empId(101l).name("John").joiningDate("2021-12-01").email("john.l@test.com").build()));
            log.info("Preloading " + repository.save(Employee.builder().empId(102l).name("Alex").joiningDate("2021-11-23").email("john.l@test.com").build()));
            log.info("Preloading " + repository.save(Employee.builder().empId(103l).name("Ryan").joiningDate("2021-10-10").email("john.l@test.com").build()));
            log.info("Preloading " + repository.save(Employee.builder().empId(104l).name("Andy").joiningDate("2021-09-14").email("john.l@test.com").build()));
        };
    }
}
