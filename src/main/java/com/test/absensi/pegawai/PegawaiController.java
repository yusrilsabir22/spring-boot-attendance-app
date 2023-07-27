package com.test.absensi.pegawai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/pegawai")
public class PegawaiController {

    @Autowired
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
