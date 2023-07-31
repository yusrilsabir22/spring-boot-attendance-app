package com.test.absensi.auth;

import com.test.absensi.exceptions.DuplicateException;
import com.test.absensi.exceptions.BadRequest;
import com.test.absensi.pegawai.Pegawai;
import com.test.absensi.pegawai.PegawaiRepository;
import com.test.absensi.user.User;
import com.test.absensi.user.Profile;
import com.test.absensi.models.Request;
import com.test.absensi.config.jwt.JwtService;
import com.test.absensi.user.UserRepository;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PegawaiRepository pegawaiRepository;
    private final JwtService jwtService;

    public AuthResponse login(Request.Login request) {
        if(request.getProfile().name().equals("ADMIN")) {
            return loginAdmin(request);
        } else {
            return loginPegawai(request);
        }
    }

    public User create(String email, String password, Profile profile) throws DuplicateException {
        var userSession = Utils.getUserSession();
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            throw new DuplicateException("Pengguna telah terdaftar.");
        }

        User newUser = new User(
                email,
                password,
                profile,
                userSession.getPerusahaan()
        );
        return userRepository.save(newUser);
    }

    public User update(User oldUser, String newPassword) {
        oldUser.setPassword(newPassword);
        return userRepository.save(oldUser);
    }

    public User update(User oldUser, String newPassword, Profile profile) {
        oldUser.setPassword(newPassword);
        oldUser.setProfile(profile);
        return userRepository.save(oldUser);
    }

    private AuthResponse loginPegawai(Request.Login request){
        Optional<Pegawai> currentPegawai = pegawaiRepository.findByEmailAndProfile(request.getEmail(), request.getProfile());
        if(currentPegawai.isEmpty()) {
            throw new BadRequest("invalid email or password");
        }
        var user = currentPegawai.get().getUser();
        boolean isPasswordValid = Objects.equals(user.getPassword(), request.getPassword());
        if(!isPasswordValid) {
            throw new BadRequest("invalid email or password");
        }

        var pegawai = currentPegawai.get();

        AuthResponse.Info info = AuthResponse.Info.builder()
                .kdJenisKelamin(pegawai.getJenisKelamin().getOrdinal())
                .kdDepartemen(pegawai.getDepartemen().getKdDepartemen())
                .namaLengkap(pegawai.getNamaLengkap())
                .kdUnitKerja(pegawai.getUnitKerja().getKdUnitKerja())
                .tanggalLahir(Utils.dateToTimestamp(pegawai.getTanggalLahir()))
                .tempatLahir(pegawai.getTempatLahir())
                .kdPendidikan(pegawai.getPendidikan().getKdPendidikan())
                .kdJabatan(pegawai.getJabatan().getKdJabatan())
                .nikUser(pegawai.getNikUser())
                .profile(pegawai.getUser().getProfile().name())
                .email(pegawai.getUser().getEmail())
                .password(pegawai.getUser().getPassword())
                .photo(pegawai.getUser().getPhoto())
                .build();
        String token = jwtService.generateToken(user);
        AuthResponse.Hasil hasil = AuthResponse.Hasil.builder()
                .info(info)
                .token(token)
                .build();
        return new AuthResponse(hasil);
    }

    private AuthResponse loginAdmin(Request.Login request) {
        Optional<User> user = userRepository.findByEmailAndProfile(request.getEmail(), request.getProfile());
        if(user.isEmpty()) {
            throw new BadRequest("invalid email or password");
        }
        boolean isPasswordValid = Objects.equals(user.get().getPassword(), request.getPassword());
        if(!isPasswordValid) {
            throw new BadRequest("invalid email or password");
        }
        String token = jwtService.generateToken(user.get());

        AuthResponse.Info info = AuthResponse.Info.builder()
                .profile(user.get().getProfile().name())
                .email(user.get().getEmail())
                .password(user.get().getPassword())
                .photo(user.get().getPhoto())
                .build();
        AuthResponse.Hasil hasil = AuthResponse.Hasil.builder()
                .info(info)
                .token(token)
                .build();
        return new AuthResponse(hasil);
    }
}
