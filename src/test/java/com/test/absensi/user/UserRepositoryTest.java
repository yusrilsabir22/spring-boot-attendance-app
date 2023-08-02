package com.test.absensi.user;

import com.test.absensi.employee.Employee;
import com.test.absensi.employee.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Test
    void itShouldThrowDataIntegrityViolationExceptionWhenSaveUserWithoutPerusahaan() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            Employee user = Employee.builder()
                    .email("test@test.com")
                    .password("test")
                    .profile(Profile.ADMIN)
                    .build();
            employeeRepository.save(user);
        });
    }
}