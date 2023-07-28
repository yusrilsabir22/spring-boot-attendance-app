package com.test.absensi.service;

import com.test.absensi.db.models.Pegawai;
import com.test.absensi.repositories.PegawaiRepository;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PegawaiService {

    private final PegawaiRepository pegawaiRepository;

    public List<Pegawai> getAllPegawai() {
        return pegawaiRepository.findAll();
    }

    public Pegawai add(Pegawai pegawai) {
        String password = Utils.randomPasswordGenerator(16);
        Pegawai newPegawai = Pegawai.builder()
                .profile(pegawai.getProfile())
                .namaLengkap(pegawai.getNamaLengkap())
                .tempatLahir(pegawai.getTempatLahir())
                .email(pegawai.getEmail())
                .password(password)
                .nikUser(pegawai.getNikUser())
                .kdJabatan(pegawai.getKdJabatan())
                .namaJabatan(pegawai.getNamaJabatan())
                .kdDepartemen(pegawai.getKdDepartemen())
                .namaDepartemen(pegawai.getNamaDepartemen())
                .kdUnitKerja(pegawai.getKdUnitKerja())
                .namaUnitKerja(pegawai.getNamaUnitKerja())
                .kdJenisKelamin(pegawai.getKdJenisKelamin())
                .namaJenisKelamin(pegawai.getNamaJenisKelamin())
                .kdPendidikan(pegawai.getKdPendidikan())
                .namaPendidikan(pegawai.getNamaPendidikan())
                .photo(pegawai.getPhoto())
                .build();

        return pegawaiRepository.save(newPegawai);
    }

}
