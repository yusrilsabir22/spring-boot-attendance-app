package com.test.absensi.presensi;

import com.test.absensi.presensi.models.*;
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
@Tag(name = "Presensi")
public class PresensiController {

    private final PresensiService presensiService;

    @Operation(
            description = "Melakukan check-in untuk pegawai",
            summary = "Untuk fungsi melakukan check-in, diambil dari jam server berjalan."
    )
    @GetMapping(path = "/in")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public ResponseEntity<PresensiResponse> checkIn() {
        boolean status = presensiService.checkIn();
        final String message = status ? "Status kehadiran berhasil diperbaharui" : "Status kehadiran gagal diperbaharui";
        PresensiResponse response = new PresensiResponse(message, status);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Melakukan check-out untuk pegawai",
            summary = "Untuk fungsi melakukan check-out, diambil dari jam server berjalan."
    )
    @GetMapping(path = "/out")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public ResponseEntity<PresensiResponse> checkOut() {
        boolean status = presensiService.checkOut();
        final String message = status ? "Status kehadiran berhasil diperbaharui" : "Status kehadiran gagal diperbaharui";
        PresensiResponse response = new PresensiResponse(message, status);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Melakukan permintaan absensi untuk pegawai",
            summary = "Digunakan pegawai untuk pemberitahuan jika absensi (tidak masuk), izinnya apakah."
    )
    @PostMapping(path = "/absensi")
    @PreAuthorize("hasAnyAuthority('user:create', 'management:create')")
    public ResponseEntity<PresensiResponse> absensi(@RequestBody @Valid RequestAbsen request) {
        boolean status = presensiService.abseni(request);
        final String message = status ? "Permintaan absen berhasil didaftarkan" : "Permintaan absen gagal didaftarkan";
        PresensiResponse response = new PresensiResponse(message, status);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Untuk fungsi melihat daftar presensi dan abensi pegawai sendiri.",
            summary = "Untuk fungsi melihat daftar presensi dan abensi pegawai sendiri."
    )
    @GetMapping(path = "/daftar/pegawai")
    @PreAuthorize("hasAnyAuthority('user:read', 'management:read')")
    public ResponseEntity<List<DaftarPresensiResponse>> daftarAbsensiPegawai() {
        return ResponseEntity.ok(presensiService.daftarPresensi());
    }

    @Operation(
            description = "Untuk fungsi melihat daftar presensi dan abensi seluruh pegawai per-range tanggal",
            summary = "Untuk fungsi melihat daftar presensi dan abensi seluruh pegawai per-range tanggal"
    )
    @GetMapping(path = "/daftar/admin")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<DaftarAdminPresensiResponse>> daftarAbsensiAdmin(
            @RequestParam String tglAwal,
            @RequestParam String tglAkhir
    ) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tglAwalDate = format.parse(tglAwal);
        Date tglAkhirDate = format.parse(tglAkhir);
        RequestAbsensiRangeDate request = new RequestAbsensiRangeDate(tglAwalDate, tglAkhirDate);

        return ResponseEntity.ok(presensiService.daftarPresensiAdmin(request));
    }
}
