package com.test.absensi.controllers;

import com.test.absensi.db.models.Pegawai;
import com.test.absensi.service.PegawaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/pegawai")
@RequiredArgsConstructor
public class PegawaiController {

    private final PegawaiService pegawaiService;

    @GetMapping(path = "/all")
    public List<Pegawai> getAll() {
        return pegawaiService.getAllPegawai();
    }

    @PostMapping(path = "/admin-tambah-pegawai")
    public ResponseEntity<String> daftar(@RequestBody Pegawai pegawai) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        return ResponseEntity.ok(pegawaiService.add(pegawai));
    }

}
