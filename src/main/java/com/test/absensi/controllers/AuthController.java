package com.test.absensi.controllers;

import com.test.absensi.db.models.User;
import com.test.absensi.models.Request;
import com.test.absensi.models.Response;
import com.test.absensi.service.PerusahaanService;
import com.test.absensi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path="api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PerusahaanService perusahaanService;
    private final UserService userService;

    @PostMapping(path="/init-data")
    public ResponseEntity<Response.InitData> initData(
            @RequestBody Request.InitData request
    ) {
        System.out.println(request);
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
            @RequestBody Request.Login request
    ) {
        return ResponseEntity.ok(userService.login(request));
    }

}
