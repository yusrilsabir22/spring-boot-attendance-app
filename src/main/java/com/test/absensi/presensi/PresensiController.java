package com.test.absensi.presensi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/presensi")
@RequiredArgsConstructor
public class PresensiController {

    private final PresensiService presensiService;

    @GetMapping(path = "/in")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public ResponseEntity<?> checkIn() {
        boolean status = presensiService.checkIn();
        final String message = status ? "Status kehadiran berhasil diperbaharui" : "Status kehadiran gagal diperbaharui";
        final HashMap response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/out")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public ResponseEntity<?> checkOut() {
        boolean status = presensiService.checkOut();
        final String message = status ? "Status kehadiran berhasil diperbaharui" : "Status kehadiran gagal diperbaharui";
        final HashMap response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/absensi")
    @PreAuthorize("hasAnyAuthority('user:create', 'management:create')")
    public ResponseEntity<?> abseni(@RequestBody @Valid RequestAbsen request) {
        boolean status = presensiService.abseni(request);
        final String message = status ? "Permintaan absen berhasil didaftarkan" : "Permintaan absen gagal didaftarkan";
        final HashMap response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/daftar/pegawai")
    @PreAuthorize("hasAnyAuthority('user:read', 'management:read')")
    public ResponseEntity<List<DaftarPresensiResponse>> daftarAbsensiPegawai() {
        return ResponseEntity.ok(presensiService.daftarPresensi());
    }

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
