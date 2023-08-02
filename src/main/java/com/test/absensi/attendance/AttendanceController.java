package com.test.absensi.attendance;

import com.test.absensi.attendance.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/presensi")
@RequiredArgsConstructor
@Tag(name = "Attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(
            description = "Melakukan check-in untuk employee",
            summary = "Untuk fungsi melakukan check-in, diambil dari jam server berjalan."
    )
    @GetMapping(path = "/in")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public ResponseEntity<AttendanceResponse> checkIn() {
        boolean status = attendanceService.checkIn();
        final String message = status ? "Status kehadiran berhasil diperbaharui" : "Status kehadiran gagal diperbaharui";
        AttendanceResponse response = new AttendanceResponse(message, status);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Melakukan check-out untuk employee",
            summary = "Untuk fungsi melakukan check-out, diambil dari jam server berjalan."
    )
    @GetMapping(path = "/out")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public ResponseEntity<AttendanceResponse> checkOut() {
        boolean status = attendanceService.checkOut();
        final String message = status ? "Status kehadiran berhasil diperbaharui" : "Status kehadiran gagal diperbaharui";
        AttendanceResponse response = new AttendanceResponse(message, status);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Melakukan permintaan absensi untuk employee",
            summary = "Digunakan employee untuk pemberitahuan jika absensi (tidak masuk), izinnya apakah."
    )
    @PostMapping(path = "/absensi")
    @PreAuthorize("hasAnyAuthority('user:create', 'management:create')")
    public ResponseEntity<AttendanceResponse> absensi(@RequestBody @Valid AttendanceRequest request) {
        boolean status = attendanceService.abseni(request);
        final String message = status ? "Permintaan absen berhasil didaftarkan" : "Permintaan absen gagal didaftarkan";
        AttendanceResponse response = new AttendanceResponse(message, status);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Untuk fungsi melihat daftar presensi dan abensi employee sendiri.",
            summary = "Untuk fungsi melihat daftar presensi dan abensi employee sendiri."
    )
    @GetMapping(path = "/daftar/pegawai")
    @PreAuthorize("hasAnyAuthority('user:read', 'management:read')")
    public ResponseEntity<List<UserListAttendanceResponse>> daftarAbsensiPegawai() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @Operation(
            description = "Untuk fungsi melihat daftar presensi dan abensi seluruh employee per-range tanggal",
            summary = "Untuk fungsi melihat daftar presensi dan abensi seluruh employee per-range tanggal"
    )
    @GetMapping(path = "/daftar/admin")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<AdminListAttendanceiResponse>> daftarAbsensiAdmin(
            @RequestParam String tglAwal,
            @RequestParam String tglAkhir
    ) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tglAwalDate = format.parse(tglAwal);
        Date tglAkhirDate = format.parse(tglAkhir);
        AttendanceRangeDateRequest request = new AttendanceRangeDateRequest(tglAwalDate, tglAkhirDate);

        return ResponseEntity.ok(attendanceService.daftarPresensiAdmin(request));
    }
}
