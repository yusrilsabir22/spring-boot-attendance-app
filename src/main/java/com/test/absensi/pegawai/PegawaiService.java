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
import com.test.absensi.user.User;
import com.test.absensi.user.UserRepository;
import com.test.absensi.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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

    private final UserRepository userRepository;

    public List<Response.User> getAllPegawai() {
        var userSession = Utils.getUserSession();
        List<Pegawai> pegawais = pegawaiRepository.findAllByPerusahaan(userSession.getPerusahaan());
        return Response.User.toList(pegawais);
    }

    // TODO need to optimize transaction query
    public Pegawai add(RequestPegawai pegawai) {
        var userSession = Utils.getUserSession();
        Optional<Pegawai> currentPegawai = pegawaiRepository.findOneByNikUser(pegawai.getNikUser(), userSession.getPerusahaan());

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

        if(pegawai.getKdJenisKelamin() < 0) {
            throw new BadRequest("Jenis kelamin tidak ditemukan");
        }
        final int kdJenisKelamin = pegawai.getKdJenisKelamin() == 0 ? 0 : pegawai.getKdJenisKelamin() - 1;

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
                .jenisKelamin(TypeJenisKelamin.values()[kdJenisKelamin])
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
                .kdJenisKelamin(typeJenisKelamin.getOrdinal())
                .namaJenisKelamin(typeJenisKelamin.name())
                .build())
                .collect(Collectors.toList());
    }

    // TODO need to optimize transaction query
    public Pegawai ubahPegawai(RequestPegawai request) {
        var userSession = Utils.getUserSession();
        Optional<Pegawai> currentPegawai = pegawaiRepository.findOneByNikUser(request.getNikUser(), userSession.getPerusahaan());

        if(currentPegawai.isEmpty()) {
            throw new DuplicateException("NIK Pegawai tidak ditemukan");
        }

        Pegawai pegawai = currentPegawai.get();

        Optional<Departemen> departemen = departemenRepository.findByKdDepartemen(request.getKdDepartemen());

        if(departemen.isEmpty()) {
            throw new BadRequest("departemen tidak ditemukan");
        }

        Optional<Jabatan> jabatan = jabatanRepository.findByKdJabatan(request.getKdJabatan());

        if(jabatan.isEmpty()) {
            throw new BadRequest("jabatan tidak ditemukan");
        }

        Optional<Pendidikan> pendidikan = pendidikanRepository.findByKdPendidikan(request.getKdPendidikan());

        if(pendidikan.isEmpty()) {
            throw new BadRequest("pendidikan tidak ditemukan");
        }

        Optional<UnitKerja> unitKerja = unitKerjaRepository.findByKdUnitKerja(request.getKdUnitKerja());

        if(unitKerja.isEmpty()) {
            throw new BadRequest("unit kerja tidak ditemukan");
        }

        if(request.getKdJenisKelamin() < 0) {
            throw new BadRequest("Jenis kelamin tidak ditemukan");
        }
        final int kdJenisKelamin = request.getKdJenisKelamin() == 0 ? 0 : request.getKdJenisKelamin() - 1;

        pegawai.setDepartemen(departemen.get());
        pegawai.setJabatan(jabatan.get());
        pegawai.setPendidikan(pendidikan.get());
        pegawai.setUnitKerja(unitKerja.get());
        pegawai.setJenisKelamin(TypeJenisKelamin.values()[kdJenisKelamin]);
        pegawai.setNamaLengkap(request.getNamaLengkap());
        pegawai.setNikUser(request.getNikUser());
        pegawai.setTempatLahir(request.getTempatLahir());

        pegawai.setTanggalLahir(new Date(TimeUnit.SECONDS.toMillis(request.getTanggalLahir())));
        authService.update(pegawai.getUser(), request.getPassword(), request.getProfile());
        return pegawaiRepository.save(pegawai);
    }

    public boolean ubahPhotoPegawai(String photoURL) {
        var user = Utils.getUserSession();
        user.setPhoto(photoURL);
        userRepository.save(user);
        return true;
    }

    public List<Response.User> getPegawaiDepartemenHRD() {
        var userSession = Utils.getUserSession();
        var perusahaan = userSession.getPerusahaan();

        Optional<Departemen> currentDepartemen = departemenRepository.findByNamaDepartemen("HRD");
        if(currentDepartemen.isEmpty()) {
            throw new BadRequest("Departemen HRD tidak di terdaftar");
        }
        var departemen = currentDepartemen.get();

        var daftarPegawai = pegawaiRepository.findAllByDepartemenAndPerusahaan(departemen, perusahaan);
        return Response.User.toList(daftarPegawai);
    }
}
