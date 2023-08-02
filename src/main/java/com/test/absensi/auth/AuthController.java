package com.test.absensi.auth;

import com.test.absensi.company.CompanyService;
import com.test.absensi.models.Request;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final CompanyService companyService;
    private final AuthService authService;

    @Operation(
            description = "Untuk membuatkan data awal company dan admin perusahan.",
            summary = "Untuk membuatkan data awal company dan admin perusahan."
    )
    @PostMapping(path="/init-data")
    public ResponseEntity<AuthResponse> initData(
            @RequestBody @Valid Request.InitData request
    ) {
        AuthResponse response = companyService.initData(request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Login bagi user untuk mendapatkan hak akses berupa token, nanti token tersebut harus ada dimasukkan di setiap header Authorization, tanggal lahir berupa Epoch, sisanya informasi bisa didapatkan dari combo employee.",
            summary = "Login bagi user untuk mendapatkan hak akses berupa token, nanti token tersebut harus ada dimasukkan di setiap header Authorization, tanggal lahir berupa Epoch, sisanya informasi bisa didapatkan dari combo employee."
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
    @PostMapping(path = "/change-password")
    @PreAuthorize("hasAnyAuthority('user:update', 'management:update')")
    public String ubahPasswordSendiri(@RequestBody AuthChangePasswordRequest request) {
        return authService.updatePassword(request);
    }

}
