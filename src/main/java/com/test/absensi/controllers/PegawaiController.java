package com.test.absensi.controllers;

import com.test.absensi.db.models.Pegawai;
import com.test.absensi.service.PegawaiService;
import lombok.RequiredArgsConstructor;
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
    public Pegawai daftar(@RequestBody Pegawai pegawai) {
        return pegawaiService.add(pegawai);
    }

}
