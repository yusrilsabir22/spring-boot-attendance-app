package com.test.absensi.pegawai;

import com.test.absensi.auth.AuthService;
import com.test.absensi.departemen.Departemen;
import com.test.absensi.departemen.DepartemenRepository;
import com.test.absensi.exceptions.BadRequest;
import com.test.absensi.exceptions.DuplicateException;
import com.test.absensi.jabatan.Jabatan;
import com.test.absensi.jabatan.JabatanRepository;
import com.test.absensi.models.TypeJenisKelamin;
import com.test.absensi.models.JenisKelamin;
import com.test.absensi.pendidikan.Pendidikan;
import com.test.absensi.pendidikan.PendidikanRepository;
import com.test.absensi.unit_kerja.UnitKerja;
import com.test.absensi.unit_kerja.UnitKerjaRepository;
import com.test.absensi.user.Profile;
import com.test.absensi.models.Response;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PegawaiService {

    private final PegawaiRepository pegawaiRepository;

    private final AuthService authService;

    private final DepartemenRepository departemenRepository;

    private final JabatanRepository jabatanRepository;

    private final PendidikanRepository pendidikanRepository;

    private final UnitKerjaRepository unitKerjaRepository;

    public List<Response.User> getAllPegawai() {
        var userSession = Utils.getUserSession();
        List<Pegawai> pegawais = pegawaiRepository.findAllByPerusahaan(userSession.getPerusahaan());
        return pegawais.stream()
                .map(pegawai -> Response.User.builder()
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
                        .build())
                .collect(Collectors.toList());
    }

    public Pegawai add(RequestPegawai pegawai) {
        var userSession = Utils.getUserSession();
        Optional<Pegawai> currentPegawai = pegawaiRepository.findOneByNikUser(pegawai.getNikUser());

        if(currentPegawai.isPresent()) {
            throw new DuplicateException("Nik Pegawai sudah terdaftar");
        }

        Optional<Departemen> departemen = departemenRepository.findByKdDepartemen(pegawai.getKdDepartemen());

        if(departemen.isEmpty()) {
            throw new BadRequest("departemen tidak ditemukan");
        }

        Optional<Jabatan> jabatan = jabatanRepository.findByKdJabatan(pegawai.getKdJabatan());

        if(jabatan.isEmpty()) {
            throw new BadRequest("jabatan tidak ditemukan");
        }

        Optional<Pendidikan> pendidikan = pendidikanRepository.findByKdPendidikan(pegawai.getKdPendidikan());

        if(pendidikan.isEmpty()) {
            throw new BadRequest("pendidikan tidak ditemukan");
        }

        Optional<UnitKerja> unitKerja = unitKerjaRepository.findByKdUnitKerja(pegawai.getKdUnitKerja());

        if(unitKerja.isEmpty()) {
            throw new BadRequest("unit kerja tidak ditemukan");
        }

        String password = pegawai.getPassword(); //Utils.randomPasswordGenerator(16);
        String email = Utils.generateEmail(pegawai.getNamaLengkap(), userSession.getPerusahaan().getNama());
        var user = authService.create(email, password, Profile.USER);

        Pegawai newPegawai = Pegawai.builder()
                .namaLengkap(pegawai.getNamaLengkap())
                .tempatLahir(pegawai.getTempatLahir())
                .tanggalLahir(new Date(TimeUnit.SECONDS.toMillis(pegawai.getTanggalLahir())))
                .nikUser(pegawai.getNikUser())
                .user(user)
                .departemen(departemen.get())
                .jabatan(jabatan.get())
                .pendidikan(pendidikan.get())
                .unitKerja(unitKerja.get())
                .jenisKelamin(TypeJenisKelamin.values()[pegawai.getKdJenisKelamin()])
                .build();

        return pegawaiRepository.save(newPegawai);
    }

    public List<Departemen> getAllDepartemen() {
        var userSession = Utils.getUserSession();
        return departemenRepository.findAllByNamaPerusahaan(userSession.getPerusahaan().getNama());
    }

    public List<Jabatan> getAllJabatan() {
        var userSession = Utils.getUserSession();
        return jabatanRepository.findAllByNamaPerusahaan(userSession.getPerusahaan().getNama());
    }

    public List<Pendidikan> getAllPendidikan() {
        var userSession = Utils.getUserSession();
        return pendidikanRepository.findAllByNamaPerusahaan(userSession.getPerusahaan().getNama());
    }

    public List<UnitKerja> getAllUnitKerja() {
        var userSession = Utils.getUserSession();
        return unitKerjaRepository.findAllByNamaPerusahaan(userSession.getPerusahaan().getNama());
    }

    public List<JenisKelamin> getAllJenisKelamin() {
        return Arrays.stream(TypeJenisKelamin.values()).map(typeJenisKelamin -> JenisKelamin.builder()
                .kdJenisKelamin(typeJenisKelamin.ordinal())
                .namaJenisKelamin(typeJenisKelamin.name())
                .build())
                .collect(Collectors.toList());
    }
}
