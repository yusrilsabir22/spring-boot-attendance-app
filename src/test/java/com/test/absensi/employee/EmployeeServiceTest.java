package com.test.absensi.employee;

import com.test.absensi.auth.AuthResponse;
import com.test.absensi.exceptions.DuplicateException;
import com.test.absensi.models.Request;
import com.test.absensi.models.Response;
import com.test.absensi.company.CompanyService;
import com.test.absensi.user.Profile;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @Mock
    private Authentication auth;

    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceTest.class);

    private static final EmployeeRequest EMPLOYEE_REQUEST = EmployeeRequest.builder()
            .email("test")
            .deptId(1)
            .jobId(1)
            .sexId(1)
            .educationId(1)
            .workUnitId(1)
            .fullname("test")
            .nin("test123")
            .password("test123")
            .profile(Profile.USER)
            .dateOfBirth(9000000)
            .build();

    @BeforeEach
    public void setUp() {
        logger.info("SETTING UP PEGAWAI SERVICE TEST...");
        Request.InitData initData = Request.InitData.builder()
                .adminName("test23")
                .company("test")
                .build();
        AuthResponse user = companyService.initData(initData);
        SecurityContextHolder.getContext().setAuthentication(auth);
        Mockito.when(auth.getPrincipal()).thenReturn(user.getResult().getInfo());
    }

    @Test
    @Order(1)
    void itShouldReturnPegawai_add() {
        Response.User employeeResponse = employeeService.add(EMPLOYEE_REQUEST);
        assertNotNull(employeeResponse);
    }

    @Test
    @Order(2)
    void itShouldReturnListPegawai_getAllPegawai() {
        List<Response.User> listPegawai = employeeService.getAllPegawai();
        assertTrue(!listPegawai.isEmpty());
    }

    @Test
    @Order(3)
    void itShouldThrowError_add() {
        Assertions.assertThrows(DuplicateException.class, () -> {
            employeeService.add(EMPLOYEE_REQUEST);
        });
    }
}