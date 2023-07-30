package com.test.absensi.pegawai;

import com.test.absensi.departemen.Departemen;
import com.test.absensi.jabatan.Jabatan;
import com.test.absensi.models.JenisKelamin;
import com.test.absensi.models.Response;
import com.test.absensi.pendidikan.Pendidikan;
import com.test.absensi.unit_kerja.UnitKerja;
import com.test.absensi.utils.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/pegawai")
@RequiredArgsConstructor
public class PegawaiController {

    private final PegawaiService pegawaiService;

    @GetMapping(path = "/daftar")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public List<Response.User> getAll() {
        return pegawaiService.getAllPegawai();
    }

    @PostMapping(path = "/admin-tambah-pegawai")
    @PreAuthorize("hasAnyAuthority('admin:create', 'management:create')")
    public Response.User daftar(@RequestBody @Valid RequestPegawai request) {
        Pegawai pegawai = pegawaiService.add(request);
        return Response.User.builder()
                .nikUser(pegawai.getNikUser())
                .namaLengkap(pegawai.getNamaLengkap())
                .tempatLahir(pegawai.getTempatLahir())
                .tanggalLahir(Utils.dateToTimestamp(pegawai.getTanggalLahir()))
                .email(pegawai.getUser().getUsername())
                .password(pegawai.getUser().getPassword())
                .profile(pegawai.getUser().getProfile())
                .kdDepartemen(pegawai.getDepartemen().getKdDepartemen())
                .namaDepartemen(pegawai.getDepartemen().getNamaDepartemen())
                .kdJabatan(pegawai.getJabatan().getKdJabatan())
                .namaJabatan(pegawai.getJabatan().getNamaJabatan())
                .kdPendidikan(pegawai.getPendidikan().getKdPendidikan())
                .namaPendidikan(pegawai.getPendidikan().getNamaPendidikan())
                .kdUnitKerja(pegawai.getUnitKerja().getKdUnitKerja())
                .namaUnitKerja(pegawai.getUnitKerja().getNamaUnitKerja())
                .kdJenisKelamin(pegawai.getJenisKelamin().ordinal())
                .namaJenisKelamin(pegawai.getJenisKelamin().name())
                .photo(pegawai.getPhoto())
                .createdAt(Utils.dateToTimestamp(pegawai.getCreatedAt()))
                .updatedAt(Utils.dateToTimestamp(pegawai.getUpdatedAt()))
                .build();
    }

    @PostMapping(path = "/ubah-photo")
    @PreAuthorize("hasAuthority('user:update')")
    public String ubahPassword(@RequestBody Map<String, Object> request) {
        return "ok";
    }

    @GetMapping(path = "/combo/departemen")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Departemen>> getAllDepartemen() {
        return ResponseEntity.ok(pegawaiService.getAllDepartemen());
    }

    @GetMapping(path = "/combo/jabatan")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Jabatan>> getAllJabatan() {
        return ResponseEntity.ok(pegawaiService.getAllJabatan());
    }

    @GetMapping(path = "/combo/pendidikan")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Pendidikan>> getAllPendidikan() {
        return ResponseEntity.ok(pegawaiService.getAllPendidikan());
    }

    @GetMapping(path="/combo/unit-kerja")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<UnitKerja>> getAllUnitKerja() {
        return ResponseEntity.ok(pegawaiService.getAllUnitKerja());
    }

    @GetMapping(path = "/combo/jenis-kelamin")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<JenisKelamin>> getAllJenisKelamin() {
        return ResponseEntity.ok(pegawaiService.getAllJenisKelamin());
    }
}
