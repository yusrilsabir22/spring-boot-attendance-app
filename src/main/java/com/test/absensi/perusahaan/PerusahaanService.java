package com.test.absensi.perusahaan;


import com.test.absensi.departemen.Departemen;
import com.test.absensi.departemen.DepartemenRepository;
import com.test.absensi.jabatan.Jabatan;
import com.test.absensi.jabatan.JabatanRepository;
import com.test.absensi.pendidikan.Pendidikan;
import com.test.absensi.pendidikan.PendidikanRepository;
import com.test.absensi.presensi.StatusAbsensi;
import com.test.absensi.presensi.StatusAbsensiRepository;
import com.test.absensi.unit_kerja.UnitKerja;
import com.test.absensi.unit_kerja.UnitKerjaRepository;
import com.test.absensi.user.User;
import com.test.absensi.models.*;
import com.test.absensi.user.Profile;
import com.test.absensi.user.UserRepository;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerusahaanService {

    private final PerusahaanRepository perusahaanRepository;

    private final UserRepository userRepository;

    private final DepartemenRepository departemenRepository;

    private final JabatanRepository jabatanRepository;

    private final PendidikanRepository pendidikanRepository;

    private final UnitKerjaRepository unitKerjaRepository;

    private final StatusAbsensiRepository statusAbsensiRepository;

    public User initData(Request.InitData request) {

        String password = Utils.randomPasswordGenerator(16);
        String email = request.getNamaAdmin()+"@"+request.getPerusahaan()+".com";
        Profile profile = Profile.ADMIN;

        Optional<Perusahaan> currentPerusahaan = perusahaanRepository.findOne(request.getPerusahaan());
        final Perusahaan perusahaan;
        if(currentPerusahaan.isEmpty()) {
            Perusahaan newPerusahaan = new Perusahaan(request.getPerusahaan());
            perusahaan = perusahaanRepository.save(newPerusahaan);
        } else {
            perusahaan = currentPerusahaan.get();
        }

        Optional<Departemen> currentDepartemen = departemenRepository.findByNamaDepartemen(Utils.DEFAULT_DEPARTEMEN);

        if(currentDepartemen.isEmpty()) {
            departemenRepository.save(new Departemen(Utils.DEFAULT_DEPARTEMEN));
        }

        Optional<Jabatan> currentJabatan = jabatanRepository.findByNamaJabatan(Utils.DEFAULT_JABATAN);

        if(currentJabatan.isEmpty()) {
            jabatanRepository.save(new Jabatan(Utils.DEFAULT_JABATAN));
        }

        Optional<Pendidikan> currentPendidikan = pendidikanRepository.findByNamaPendidikan(Utils.DEFAULT_PENDIDIKAN);

        if(currentPendidikan.isEmpty()) {
            pendidikanRepository.save(new Pendidikan(Utils.DEFAULT_PENDIDIKAN));
        }

        Optional<UnitKerja> currentUnitKerja = unitKerjaRepository.findByNamaUnitKerja(Utils.DEFAULT_UNIT_KERJA);

        if(currentUnitKerja.isEmpty()) {
            unitKerjaRepository.save(new UnitKerja(Utils.DEFAULT_UNIT_KERJA));
        }

        statusAbsensiRepository.saveAll(Utils.getDefaultStatusAbsensi());

        Optional<User> currentUser = userRepository.findByEmail(email);
        if(currentUser.isPresent()) {
            return currentUser.get();
        }


        User user = new User(email, password, profile, perusahaan);

        return userRepository.save(user);
    }


    public List<Perusahaan> getAllPerusahaan() {
        return perusahaanRepository.findAll();
    }

}
