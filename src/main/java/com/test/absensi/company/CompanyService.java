package com.test.absensi.company;


import com.test.absensi.auth.AuthResponse;
import com.test.absensi.config.jwt.JwtService;
import com.test.absensi.department.Department;
import com.test.absensi.department.DepartmentRepository;
import com.test.absensi.employee.Employee;
import com.test.absensi.employee.EmployeeRepository;
import com.test.absensi.job.Job;
import com.test.absensi.job.JobRepository;
import com.test.absensi.education.Education;
import com.test.absensi.education.EducationRepository;
import com.test.absensi.attendance_status.AttendanceStatusRepository;
import com.test.absensi.token.Token;
import com.test.absensi.token.TokenRepository;
import com.test.absensi.token.TokenType;
import com.test.absensi.work_unit.WorkUnit;
import com.test.absensi.work_unit.WorkUnitRepository;
import com.test.absensi.models.*;
import com.test.absensi.user.Profile;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final DepartmentRepository departementRepository;

    private final JobRepository jobRepository;

    private final EducationRepository educationRepository;

    private final WorkUnitRepository workUnitRepository;

    private final AttendanceStatusRepository attendanceStatusRepository;

    private final EmployeeRepository employeeRepository;
    
    private final JwtService jwtService;
    
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse initData(Request.InitData request) {

        String password = Utils.randomPasswordGenerator(16);
        String email = request.getAdminName()+"@"+request.getCompany()+".com";
        Profile profile = Profile.ADMIN;

        Optional<Company> currentCompany = companyRepository.findByName(request.getCompany());
        final Company company;
        if(currentCompany.isEmpty()) {
            Company newCompany = new Company(request.getCompany(), request.getAdminName());
            company = companyRepository.save(newCompany);
        } else {
            company = currentCompany.get();
        }

        Optional<Department> currentDepartement = departementRepository.findByName(Utils.DEFAULT_DEPARTEMEN);

        if(currentDepartement.isEmpty()) {
            departementRepository.save(new Department(Utils.DEFAULT_DEPARTEMEN));
        }

        Optional<Job> currentJob = jobRepository.findByName(Utils.DEFAULT_JABATAN);

        if(currentJob.isEmpty()) {
            jobRepository.save(new Job(Utils.DEFAULT_JABATAN));
        }

        Optional<Education> currentEducation = educationRepository.findByNamaPendidikan(Utils.DEFAULT_PENDIDIKAN);

        if(currentEducation.isEmpty()) {
            educationRepository.save(new Education(Utils.DEFAULT_PENDIDIKAN));
        }

        Optional<WorkUnit> currentWorkUnit = workUnitRepository.findByName(Utils.DEFAULT_UNIT_KERJA);

        if(currentWorkUnit.isEmpty()) {
            workUnitRepository.save(new WorkUnit(Utils.DEFAULT_UNIT_KERJA));
        }

        attendanceStatusRepository.saveAll(Utils.getDefaultStatusAbsensi());

        Optional<Employee> currentUser = employeeRepository.findByEmail(email);
        final Employee savedUser;
        if(currentUser.isPresent()) {
            savedUser = currentUser.get();
        } else {
            Employee newUser = Employee.builder()
                    .fullname(request.getAdminName())
                    .email(email)
                    .profile(profile)
                    .company(company)
                    .password(passwordEncoder.encode(password))
                    .sex(Sex.MALE)
                    .build();//(email, password, profile, company);
    
            savedUser = employeeRepository.save(newUser);
        }

        var token = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, token);
        var info = AuthResponse.Info.build(savedUser);
        AuthResponse.Result result = AuthResponse.Result.builder()
                .info(info)
                .token(token)
                .refreshToken(refreshToken)
                .build();
        result.getInfo().setPassword(password);
        return AuthResponse.builder().result(result).build();
    }


    public List<Company> getAllPerusahaan() {
        return companyRepository.findAll();
    }

    private void saveUserToken(Employee employee, String token) {
        var jwtToken = Token.builder()
                .employee(employee)
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(jwtToken);
    }
}
