package com.test.absensi.auth;

import com.test.absensi.user.User;
import com.test.absensi.models.Request;
import com.test.absensi.models.Response;
import com.test.absensi.perusahaan.PerusahaanService;
import com.test.absensi.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path="/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final PerusahaanService perusahaanService;
    private final AuthService authService;

    @Operation(
            description = "Untuk membuatkan data awal perusahaan dan admin perusahan.",
            summary = "Untuk membuatkan data awal perusahaan dan admin perusahan."
    )
    @PostMapping(path="/init-data")
    public ResponseEntity<Response.InitData> initData(
            @RequestBody @Valid Request.InitData request
    ) {
        User user = perusahaanService.initData(request);
        Response.InitData response = Response.InitData.builder()
                .email(user.getUsername())
                .password(user.getPassword())
                .profile(user.getProfile())
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Login bagi user untuk mendapatkan hak akses berupa token, nanti token tersebut harus ada dimasukkan di setiap header Authorization, tanggal lahir berupa Epoch, sisanya informasi bisa didapatkan dari combo pegawai.",
            summary = "Login bagi user untuk mendapatkan hak akses berupa token, nanti token tersebut harus ada dimasukkan di setiap header Authorization, tanggal lahir berupa Epoch, sisanya informasi bisa didapatkan dari combo pegawai."
    )
    @PostMapping(path="/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid Request.Login request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
            description = "Digunakan oleh user untuk mengubah passwordnya sendiri yang awalnya passwordnya dibuatkan oleh admin",
            summary = "Digunakan oleh user untuk mengubah passwordnya sendiri yang awalnya passwordnya dibuatkan oleh admin"
    )
    @PostMapping(path = "/ubah-password-sendiri")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public String ubahPasswordSendiri(@RequestBody AuthRequest request) {
        var userSession = Utils.getUserSession();

        if(
                !Objects.equals(request.getPasswordAsli(), userSession.getPassword()) ||
                !Objects.equals(request.getPasswordBaru1(), request.getPasswordBaru2())
        ) {
            throw new UsernameNotFoundException("invalid email or password");
        }

        authService.update(userSession, request.getPasswordBaru1());
        return "success ubah password";
    }

}
