package com.test.absensi.controllers;

import com.test.absensi.exceptions.EtAuthException;
import com.test.absensi.models.Perusahaan;
import com.test.absensi.models.User;
import com.test.absensi.service.PerusahaanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
public class PerusahaanController {

    @Autowired
    private PerusahaanService perusahaanService;


//    @PostMapping(path = "/auth/init-data")
//    public ResponseEntity<?> initData(@RequestBody Map<String, Object> req) {
//        String namaAdmin = (String) req.get("namaAdmin");
//        String perusahaan = (String) req.get("perusahaan");
//        if(namaAdmin == null || perusahaan == null) {
//            throw new IllegalArgumentException();
//        }
//
//        User result = perusahaanService.initData(namaAdmin, perusahaan);
//
//        HashMap<String, String> res = new HashMap<>();
//        res.put("email", result.getUsername());
//        res.put("password", result.getPassword());
//        res.put("profile", result.getProfile().name());
//        return ResponseEntity.ok(res);
//    }

//    @PostMapping(path = "/auth/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, Object> req) {
//        String email = (String) req.get("email");
//        String password = (String) req.get("password");
//        String profile = (String) req.get("profile");
//
//        if(email == null || password == null || profile == null) {
//            throw new EtAuthException("failed, please check your email, password and profile!");
//        }
//
//        Optional<Perusahaan> perusahaan = user.getPerusahaan(email, password, profile);
//
//        if(!perusahaan.isPresent()) {
//            throw new EtAuthException("failed, please check your email, password and profile!");
//        } else {
//            return ResponseEntity.ok(perusahaan);
//        }
//
//    }

    @GetMapping(path = "/perusahaan")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(perusahaanService.getAllPerusahaan());
    }

}
