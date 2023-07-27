package com.test.absensi.pegawai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PegawaiService {

    @Autowired
    private PegawaiRepository pegawaiRepository;

    public List<Pegawai> getAllPegawai() {
        return pegawaiRepository.findAll();
    }

    public Pegawai add(Pegawai pegawai) {
        return pegawai;
    }

//    new Pegawai(
//                "profile",
//                        "namaLengkap",
//                        "tempatLahir",
//                        1000000,
//                        "email",
//                        "password",
//                        "nikUser",
//                        001,
//                        "namaJabatan",
//                        002,
//                        "namaDepartemen",
//                        003,
//                        "namaUnitKerja",
//                        004,
//                        "namaJenisKelamin",
//                        005,
//                        "namaPendidikan",
//                        "photo"
//    );

}
