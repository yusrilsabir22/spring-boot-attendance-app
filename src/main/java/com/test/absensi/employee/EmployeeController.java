package com.test.absensi.employee;

import com.test.absensi.department.Department;
import com.test.absensi.job.Job;
import com.test.absensi.models.Sex;
import com.test.absensi.models.Response;
import com.test.absensi.education.Education;
import com.test.absensi.work_unit.WorkUnit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employee")
@RequiredArgsConstructor
@Tag(name = "Employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            description = "Digunakan untuk mendapatkan data seluruh employee, hanya login Admin dan atau employee dari department HRD yang bisa mengakses.",
            summary = "Digunakan untuk mendapatkan data seluruh employee, hanya login Admin dan atau employee dari department HRD yang bisa mengakses."
    )
    @GetMapping(path = "")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public List<Response.User> getAll() {
        return employeeService.getAllPegawai();
    }

    @Operation(
            description = "Untuk fungsi menambahkan employee baru dan hanya Admin dan atau employee dari departeman HRD yang bisa mengakses.",
            summary = "Untuk fungsi menambahkan employee baru dan hanya Admin dan atau employee dari departeman HRD yang bisa mengakses."
    )
    @PostMapping(path = "/admin-add-employee")
    @PreAuthorize("hasAnyAuthority('admin:create', 'management:create')")
    public Response.User daftar(@RequestBody @Valid EmployeeRequest request) {
        return employeeService.add(request);
    }

    @Operation(
            description = "Untuk fungsi mengubah employee baru dan hanya Admin dan atau employee dari departeman HRD yang bisa mengakses.",
            summary = "Untuk fungsi mengubah employee baru dan hanya Admin dan atau employee dari departeman HRD yang bisa mengakses."
    )
    @PostMapping(path = "/admin-update-employee")
    @PreAuthorize("hasAnyAuthority('admin:update', 'management:update')")
    public Response.User adminUbahPegawai(
            @RequestBody EmployeeRequest request
    ) {
        return employeeService.update(request);
    }

    @Operation(
            description = "Untuk fungsi mengubah photo employee dan hanya Admin dan atau employee dari departeman HRD yang bisa mengakses.",
            summary = "Untuk fungsi mengubah photo employee dan hanya Admin dan atau employee dari departeman HRD yang bisa mengakses."
    )
    @PostMapping(path = "/admin-change-photo")
    @PreAuthorize("hasAnyAuthority('admin:update')")
    public boolean adminUbahFoto(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestPart String namaFile
    ) throws IOException {
        return employeeService.changePhoto(multipartFile, namaFile);
    }

    @Operation(
            description = "Untuk fungsi mengubah photo sendiri bagi employee.",
            summary = "Untuk fungsi mengubah photo sendiri bagi employee."
    )
    @PostMapping(path = "/change-photo")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public boolean ubahPhoto(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestPart String namaFile
    ) throws IOException {
        return employeeService.changePhoto(multipartFile, namaFile);
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box employee yang berada di department HRD.",
            summary = "Digunakan untuk mendapatkan data combo-box employee yang berada di department HRD."
    )
    @GetMapping(path = "/combo/departemen-hrd")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Response.User>> getAllDepartemenHRD() {
        return ResponseEntity.ok(employeeService.getAllEmployeeOfDepartemen("HRD"));
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box department.",
            summary = "Digunakan untuk mendapatkan data combo-box department."
    )
    @GetMapping(path = "/combo/departemen")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Department>> getAllDepartemen() {
        return ResponseEntity.ok(employeeService.getAllDepartement());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box jabatan.",
            summary = "Digunakan untuk mendapatkan data combo-box jabatan."
    )
    @GetMapping(path = "/combo/jabatan")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Job>> getAllJabatan() {
        return ResponseEntity.ok(employeeService.getAllJob());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box pendidikan.",
            summary = "Digunakan untuk mendapatkan data combo-box pendidikan."
    )
    @GetMapping(path = "/combo/pendidikan")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Education>> getAllPendidikan() {
        return ResponseEntity.ok(employeeService.getAllEducation());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box unit kerja.",
            summary = "Digunakan untuk mendapatkan data combo-box unit kerja."
    )
    @GetMapping(path="/combo/unit-kerja")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<WorkUnit>> getAllUnitKerja() {
        return ResponseEntity.ok(employeeService.getAllWorkUnit());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box jenis kelamin.",
            summary = "Digunakan untuk mendapatkan data combo-box jenis kelamin."
    )
    @GetMapping(path = "/combo/jenis-kelamin")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Sex>> getAllJenisKelamin() {
        return ResponseEntity.ok(employeeService.getAllSex());
    }
}
