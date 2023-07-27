package com.test.absensi.controllers;

import com.test.absensi.models.Pegawai;
import com.test.absensi.models.Profile;
import com.test.absensi.service.PegawaiService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/pegawai")
@RequiredArgsConstructor
public class PegawaiController {

    private PegawaiService pegawaiService;

    @GetMapping(path = "/all")
    public List<Pegawai> getAll() {
        return pegawaiService.getAllPegawai();
    }

    @PostMapping(path = "/daftar")
    public Pegawai daftar(@RequestBody Pegawai pegawai) {
        return pegawaiService.add(pegawai);
    }

}
