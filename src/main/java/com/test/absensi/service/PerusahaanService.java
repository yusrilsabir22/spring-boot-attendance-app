package com.test.absensi.service;


import com.test.absensi.db.models.Perusahaan;
import com.test.absensi.db.models.User;
import com.test.absensi.models.*;
import com.test.absensi.repositories.PerusahaanRepository;
import com.test.absensi.repositories.UserRepository;
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

    private final JwtService jwtService;

    public User initData(Request.InitData request) {

        String password = Utils.randomPasswordGenerator(16);
        String email = request.getNamaAdmin()+"@"+request.getPerusahaan()+".com";
        Profile profile = Profile.ADMIN;

        Optional<User> currentUser = userRepository.findByEmail(email);
        Optional<Perusahaan> currentPerusahaan = perusahaanRepository.findOne(request.getPerusahaan());
        final Perusahaan perusahaan;
        if(currentPerusahaan.isEmpty()) {
            Perusahaan newPerusahaan = new Perusahaan(request.getPerusahaan());
            perusahaan = perusahaanRepository.save(newPerusahaan);
        } else {
            perusahaan = currentPerusahaan.get();
        }

        if(currentUser.isPresent()) {
            return currentUser.get();
        }

        User user = new User(email, password, profile, perusahaan);

        return userRepository.save(user);
    }

    public Optional<Perusahaan> getPerusahaan(String perusahaan) {
        return perusahaanRepository.findOne(perusahaan);
    }

    public List<Perusahaan> getAllPerusahaan() {
        return perusahaanRepository.findAll();
    }

}
