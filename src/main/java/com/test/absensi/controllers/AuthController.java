package com.test.absensi.controllers;

import com.test.absensi.models.InitDataRequest;
import com.test.absensi.models.InitDataResponse;
import com.test.absensi.models.User;
import com.test.absensi.service.PerusahaanService;
import com.test.absensi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PerusahaanService perusahaanService;
    private final UserService userService;

    @PostMapping(path="/init-data")
    public ResponseEntity<InitDataResponse> initData(
            @RequestBody InitDataRequest request
    ) {
        System.out.println(request);
        User user = perusahaanService.initData(request);
        InitDataResponse response = new InitDataResponse(
                user.getUsername(),
                user.getPassword(),
                user.getProfile()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping(path="/login")
    public ResponseEntity<String> login(
            @RequestBody InitDataResponse request
    ) {
        return ResponseEntity.ok(userService.login(request));
    }

}
