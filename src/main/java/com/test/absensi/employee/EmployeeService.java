package com.test.absensi.employee;

import com.test.absensi.department.Department;
import com.test.absensi.department.DepartmentRepository;
import com.test.absensi.exceptions.BadRequest;
import com.test.absensi.exceptions.DuplicateException;
import com.test.absensi.job.Job;
import com.test.absensi.job.JobRepository;
import com.test.absensi.models.Sex;
import com.test.absensi.education.Education;
import com.test.absensi.education.EducationRepository;
import com.test.absensi.user.Profile;
import com.test.absensi.utils.S3Client;
import com.test.absensi.work_unit.WorkUnit;
import com.test.absensi.work_unit.WorkUnitRepository;
import com.test.absensi.models.Response;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final JobRepository jobRepository;

    private final EducationRepository educationRepository;

    private final WorkUnitRepository workUnitRepository;

    private final S3Client s3Client;

    private final PasswordEncoder passwordEncoder;


    public List<Response.User> getAllPegawai() {
        var userSession = Utils.getUserSession();
        List<Employee> employees = employeeRepository.findAllByCompany(userSession.getCompany());
        return Response.User.buildList(employees);
    }

    @Transactional
    public Response.User add(EmployeeRequest request) {
        var userSession = Utils.getUserSession();
        Optional<Employee> currentEmployee = employeeRepository.findByNIN(request.getNin(), userSession.getCompany());

        if(currentEmployee.isPresent()) {
            throw new DuplicateException("National Identity Number (NIN) is already exists");
        }

        if(request.getSexId() < 0 || request.getSexId() - 1 > Sex.values().length) {
            throw new BadRequest("There are ONLY 2 GENDER in this WORLD. if you don't accept, then please go another world");
        }
        final int sexId = request.getSexId() == 0 ? 0 : request.getSexId() - 1;

        String password = request.getPassword();
        var department = departmentRepository.getReferenceById(request.getDeptId());
        Profile profile = department.getName().contains("hrd") ? Profile.MANAGER : Profile.USER;
        Employee newEmployee = Employee.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(password))
                .company(userSession.getCompany())
                .fullname(request.getFullname())
                .placeOfBirth(request.getPlaceOfBirth())
                .dateOfBirth(new Date(TimeUnit.SECONDS.toMillis(request.getDateOfBirth())))
                .nin(request.getNin())
                .department(department)
                .job(jobRepository.getReferenceById(request.getJobId()))
                .education(educationRepository.getReferenceById(request.getEducationId()))
                .workUnit(workUnitRepository.getReferenceById(request.getWorkUnitId()))
                .sex(Sex.values()[sexId])
                .profile(profile)
                .build();

        var savedEmployee = employeeRepository.save(newEmployee);
        var response = Response.User.build(savedEmployee);
        response.setPassword(password);
        return response;
    }

    public List<Department> getAllDepartement() {
        return departmentRepository.findAll();
    }

    public List<Job> getAllJob() {
        return jobRepository.findAll();
    }

    public List<Education> getAllEducation() {
        return educationRepository.findAll();
    }

    public List<WorkUnit> getAllWorkUnit() {
        return workUnitRepository.findAll();
    }

    public List<Sex> getAllSex() {
        return Arrays.stream(Sex.values())
                .collect(Collectors.toList());
    }

    public Response.User update(EmployeeRequest request) {
        var userSession = Utils.getUserSession();
        Optional<Employee> currentEmployee = employeeRepository.findByNIN(request.getNin(), userSession.getCompany());

        if(currentEmployee.isEmpty()) {
            throw new DuplicateException("National Identity Number (NIN) is already exists");
        }

        Employee employee = currentEmployee.get();

        if(request.getSexId() < 0) {
            throw new BadRequest("There are ONLY 2 GENDER in this WORLD. if you don't accept, then please go another world");
        }
        final int sexId = request.getSexId() == 0 ? 0 : request.getSexId() - 1;

        employee.setDepartment(departmentRepository.getReferenceById(request.getDeptId()));
        employee.setJob(jobRepository.getReferenceById(request.getJobId()));
        employee.setEducation(educationRepository.getReferenceById(request.getEducationId()));
        employee.setWorkUnit(workUnitRepository.getReferenceById(request.getWorkUnitId()));
        employee.setSex(Sex.values()[sexId]);
        employee.setFullname(request.getFullname());
        employee.setNin(request.getNin());
        employee.setPlaceOfBirth(request.getPlaceOfBirth());

        employee.setDateOfBirth(Utils.timestampToDate(request.getDateOfBirth()));

        var updatedEmploy = employeeRepository.save(employee);
        return Response.User.build(updatedEmploy);
    }

    public List<Response.User> getAllEmployeeOfDepartemen(String deptName) {
        var userSession = Utils.getUserSession();
        var company = userSession.getCompany();

        Optional<Department> currentDepartment = departmentRepository.findByName(deptName);
        if(currentDepartment.isEmpty()) {
            throw new BadRequest("HR Department not registered yet");
        }
        var departement = currentDepartment.get();

        var employees = employeeRepository.findAllByDepartmentAndCompany(departement, company);
        return Response.User.buildList(employees);
    }

    public boolean changePhoto(MultipartFile multipartFile, String namaFile) throws IOException {
        System.out.println("UBAH PHOTO contentType != ? | " + Objects.equals(multipartFile.getContentType(), "image/png"));
        if (
                !(!Objects.equals(multipartFile.getContentType(), "image/jpg") ||
                        !Objects.equals(multipartFile.getContentType(), "image/png") ||
                        !Objects.equals(multipartFile.getContentType(), "image/jpeg"))
        ) {
            throw new BadRequest("format file harus gambar");
        }
        var employee = Utils.getUserSession();
        var file = Utils.convertMultiPartToFile(multipartFile);
        String url = s3Client.upload(file, namaFile);
        file.delete();
        employee.setPhoto(url);
        employeeRepository.save(employee);
        return true;
    }
}
