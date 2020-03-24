package com.derzhavets.overtimesservice;

import com.derzhavets.controller.model.Overtime;
import com.derzhavets.repository.OvertimeRepository;
import com.derzhavets.service.OvertimeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang.time.DateUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = { OvertimesServiceApplicationTests.Initializer.class })
public class OvertimesServiceApplicationTests {
    @Autowired
    private OvertimeService overtimeService;

    @Test
	void overtimeServiceHappyPass() {
        List<Overtime> overtimes = overtimeService.findAllOvertimes();

        assertThat(overtimes.size(), Matchers.greaterThan(0));
    }

    @Test
    void testCreateOvertime() {
        Integer employeeId = 1;
        Integer projectId = 2;
        Date startDate = Date.valueOf(LocalDate.of(2020, 3, 24));
        Double hours = 20d;

        Overtime overtime = new Overtime();
        overtime.setEmployeeId(employeeId);
        overtime.setProjectId(projectId);
        overtime.setStartDate(startDate);
        overtime.setHours(hours);

        Overtime createdOvertime = overtimeService.createOvertime(overtime);

        overtime = overtimeService.findOvertimeById(createdOvertime.getId().toString());

        assertThat(overtime.getEmployeeId(), equalTo(employeeId));
        assertThat(overtime.getProjectId(), equalTo(projectId));
        assertThat(DateUtils.truncate(overtime.getStartDate(), Calendar.DATE), equalTo(startDate));
        assertThat(overtime.getHours(), equalTo(hours));
    }

    @Container
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("overtimes")
            .withUsername("docker")
            .withPassword("docker");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
