package com.test.absensi.service;

import com.test.absensi.db.models.Pegawai;
import com.test.absensi.db.models.User;
import com.test.absensi.exceptions.DuplicateException;
import com.test.absensi.models.Profile;
import com.test.absensi.models.Response;
import com.test.absensi.repositories.PegawaiRepository;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PegawaiService {

    private final PegawaiRepository pegawaiRepository;
    private final UserService userService;

    public List<Response.User> getAllPegawai() {
        var userSession = Utils.getUserSession();
        List<Pegawai> pegawais = pegawaiRepository.findAllByPerusahaan(userSession.getPerusahaan());
        return pegawais.stream()
                .map(pegawai -> Response.User.builder()
                        .nikUser(pegawai.getNikUser())
                        .namaLengkap(pegawai.getNamaLengkap())
                        .tempatLahir(pegawai.getTempatLahir())
                        .tanggalLahir(pegawai.getTanggalLahir())
                        .email(pegawai.getUser().getUsername())
                        .password(pegawai.getUser().getPassword())
                        .profile(pegawai.getUser().getProfile())
                        .photo(pegawai.getPhoto())
                        .build())
                .collect(Collectors.toList());
    }

    public Pegawai add(Pegawai pegawai) throws Exception {
        var userSession = Utils.getUserSession();
        Optional<Pegawai> currentPegawai = pegawaiRepository.findOneByNikUser(pegawai.getNikUser());

        if(currentPegawai.isPresent()) {
            throw new DuplicateException("Nik Pegawai sudah ada di perusahaan "+currentPegawai.get().getPerusahaan().getNama());
        }

        String password = Utils.randomPasswordGenerator(16);
        String email = pegawai.getNamaLengkap()+"@"+userSession.getPerusahaan().getNama()+".com";
        var user = userService.create(email, password, Profile.USER);

        Pegawai newPegawai = Pegawai.builder()
                .namaLengkap(pegawai.getNamaLengkap())
                .tempatLahir(pegawai.getTempatLahir())
                .nikUser(pegawai.getNikUser())
                .user(user)
                .perusahaan(userSession.getPerusahaan())
                .photo(pegawai.getPhoto())
                .build();

        return pegawaiRepository.save(newPegawai);
    }

}
