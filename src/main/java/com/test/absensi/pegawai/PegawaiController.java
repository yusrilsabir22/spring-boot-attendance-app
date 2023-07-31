package com.test.absensi.pegawai;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.test.absensi.departemen.Departemen;
import com.test.absensi.exceptions.GlobalException;
import com.test.absensi.jabatan.Jabatan;
import com.test.absensi.models.JenisKelamin;
import com.test.absensi.models.Response;
import com.test.absensi.pendidikan.Pendidikan;
import com.test.absensi.unit_kerja.UnitKerja;
import com.test.absensi.user.UserRepository;
import com.test.absensi.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/pegawai")
@RequiredArgsConstructor
@Tag(name = "Pegawai")
public class PegawaiController {

    private final PegawaiService pegawaiService;
    private final UserRepository userRepository;

    private final AmazonS3 amazonS3Client;

    @Value("${S3_BUCKET}")
    private String S3Bucket;

    @Value("${S3_ENDPOINT_URL}")
    private String S3EndpointURL;

    @Operation(
            description = "Digunakan untuk mendapatkan data seluruh pegawai, hanya login Admin dan atau pegawai dari departemen HRD yang bisa mengakses.",
            summary = "Digunakan untuk mendapatkan data seluruh pegawai, hanya login Admin dan atau pegawai dari departemen HRD yang bisa mengakses."
    )
    @GetMapping(path = "/daftar")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public List<Response.User> getAll() {
        return pegawaiService.getAllPegawai();
    }

    @Operation(
            description = "Untuk fungsi menambahkan pegawai baru dan hanya Admin dan atau pegawai dari departeman HRD yang bisa mengakses.",
            summary = "Untuk fungsi menambahkan pegawai baru dan hanya Admin dan atau pegawai dari departeman HRD yang bisa mengakses."
    )
    @PostMapping(path = "/admin-tambah-pegawai")
    @PreAuthorize("hasAnyAuthority('admin:create', 'management:create')")
    public Response.User daftar(@RequestBody @Valid RequestPegawai request) {
        Pegawai pegawai = pegawaiService.add(request);
        return Response.User.transform(pegawai);
    }

    @Operation(
            description = "Untuk fungsi mengubah pegawai baru dan hanya Admin dan atau pegawai dari departeman HRD yang bisa mengakses.",
            summary = "Untuk fungsi mengubah pegawai baru dan hanya Admin dan atau pegawai dari departeman HRD yang bisa mengakses."
    )
    @PostMapping(path = "/admin-ubah-pegawai")
    @PreAuthorize("hasAnyAuthority('admin:update', 'management:update')")
    public Pegawai adminUbahPegawai(
            @RequestBody RequestPegawai request
    ) {
        return pegawaiService.ubahPegawai(request);
    }

    @Operation(
            description = "Untuk fungsi mengubah photo pegawai dan hanya Admin dan atau pegawai dari departeman HRD yang bisa mengakses.",
            summary = "Untuk fungsi mengubah photo pegawai dan hanya Admin dan atau pegawai dari departeman HRD yang bisa mengakses."
    )
    @PostMapping(path = "/admin-ubah-photo")
    @PreAuthorize("hasAnyAuthority('admin:update')")
    public boolean adminUbahFoto(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestPart String namaFile
    ) {
        var userSession = Utils.getUserSession();
        try {
            if(!amazonS3Client.doesBucketExist(S3Bucket)) {
                amazonS3Client.createBucket(S3Bucket);
            }
            File file = Utils.convertMultiPartToFile(multipartFile);
            amazonS3Client.putObject(S3Bucket, "Image/"+namaFile, file);
            file.delete();
            String url = S3EndpointURL+"/"+S3Bucket+"/Image/"+namaFile;
            userSession.setPhoto(url);
            userRepository.save(userSession);
            return true;
        } catch (SdkClientException | IOException e) {
            System.out.println(e.getMessage());
            throw new GlobalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(
            description = "Untuk fungsi mengubah photo sendiri bagi pegawai.",
            summary = "Untuk fungsi mengubah photo sendiri bagi pegawai."
    )
    @PostMapping(path = "/ubah-photo")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public boolean ubahPhoto(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestPart String namaFile
    ) {
        try {
            if(!amazonS3Client.doesBucketExist(S3Bucket)) {
                amazonS3Client.createBucket(S3Bucket);
            }
            File file = Utils.convertMultiPartToFile(multipartFile);
            amazonS3Client.putObject(S3Bucket, "Image/"+namaFile, file);
            file.delete();
            String url = S3EndpointURL+"/"+S3Bucket+"/Image/"+namaFile;
            return pegawaiService.ubahPhotoPegawai(url);
        } catch (SdkClientException | IOException e) {
            System.out.println(e.getMessage());
            throw new GlobalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box pegawai yang berada di departemen HRD.",
            summary = "Digunakan untuk mendapatkan data combo-box pegawai yang berada di departemen HRD."
    )
    @GetMapping(path = "/combo/departemen-hrd")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Response.User>> getAllDepartemenHRD() {
        return ResponseEntity.ok(pegawaiService.getPegawaiDepartemenHRD());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box departemen.",
            summary = "Digunakan untuk mendapatkan data combo-box departemen."
    )
    @GetMapping(path = "/combo/departemen")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Departemen>> getAllDepartemen() {
        return ResponseEntity.ok(pegawaiService.getAllDepartemen());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box jabatan.",
            summary = "Digunakan untuk mendapatkan data combo-box jabatan."
    )
    @GetMapping(path = "/combo/jabatan")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Jabatan>> getAllJabatan() {
        return ResponseEntity.ok(pegawaiService.getAllJabatan());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box pendidikan.",
            summary = "Digunakan untuk mendapatkan data combo-box pendidikan."
    )
    @GetMapping(path = "/combo/pendidikan")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<Pendidikan>> getAllPendidikan() {
        return ResponseEntity.ok(pegawaiService.getAllPendidikan());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box unit kerja.",
            summary = "Digunakan untuk mendapatkan data combo-box unit kerja."
    )
    @GetMapping(path="/combo/unit-kerja")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<UnitKerja>> getAllUnitKerja() {
        return ResponseEntity.ok(pegawaiService.getAllUnitKerja());
    }

    @Operation(
            description = "Digunakan untuk mendapatkan data combo-box jenis kelamin.",
            summary = "Digunakan untuk mendapatkan data combo-box jenis kelamin."
    )
    @GetMapping(path = "/combo/jenis-kelamin")
    @PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
    public ResponseEntity<List<JenisKelamin>> getAllJenisKelamin() {
        return ResponseEntity.ok(pegawaiService.getAllJenisKelamin());
    }
}
