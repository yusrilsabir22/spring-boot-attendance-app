package com.test.absensi.service;

import com.test.absensi.db.models.Pegawai;
import com.test.absensi.db.models.User;
import com.test.absensi.repositories.PegawaiRepository;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PegawaiService {

    private final PegawaiRepository pegawaiRepository;
    private final UserService userService;

    public List<Pegawai> getAllPegawai() {
        return pegawaiRepository.findAll();
    }

    public String add(Pegawai pegawai) {
//        String password = Utils.randomPasswordGenerator(16);
//        String email = pegawai.getNamaLengkap()+"@"+pegawai.getNamaLengkap()+".com";
//        User user =
//        Pegawai newPegawai = Pegawai.builder()
//                .namaLengkap(pegawai.getNamaLengkap())
//                .tempatLahir(pegawai.getTempatLahir())
//                .nikUser(pegawai.getNikUser())
//                .user()
//                .photo(pegawai.getPhoto())
//                .build();
//
//        return pegawaiRepository.save(newPegawai);
        return "ok";
    }

}
