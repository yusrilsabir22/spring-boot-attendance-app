package com.test.absensi.auth;

import com.test.absensi.user.User;
import com.test.absensi.models.Request;
import com.test.absensi.models.Response;
import com.test.absensi.perusahaan.PerusahaanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PerusahaanService perusahaanService;
    private final AuthService authService;

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

    @PostMapping(path="/login")
    public ResponseEntity<String> login(
            @RequestBody @Valid Request.Login request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

}
