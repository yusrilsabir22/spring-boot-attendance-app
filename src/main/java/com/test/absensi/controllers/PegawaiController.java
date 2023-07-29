package com.test.absensi.controllers;

import com.test.absensi.db.models.Pegawai;
import com.test.absensi.db.models.User;
import com.test.absensi.exceptions.BadRequest;
import com.test.absensi.models.Response;
import com.test.absensi.service.PegawaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/pegawai")
@RequiredArgsConstructor
public class PegawaiController {

    private final PegawaiService pegawaiService;

    @GetMapping(path = "/all")
    public List<Response.User> getAll() {
        return pegawaiService.getAllPegawai();
    }

    @PostMapping(path = "/admin-tambah-pegawai")
    public Response.User daftar(@RequestBody Pegawai request) throws Exception {
        Pegawai pegawai = pegawaiService.add(request);
        return Response.User.builder()
                .nikUser(pegawai.getNikUser())
                .namaLengkap(pegawai.getNamaLengkap())
                .tempatLahir(pegawai.getTempatLahir())
                .tanggalLahir(pegawai.getTanggalLahir())
                .email(pegawai.getUser().getUsername())
                .password(pegawai.getUser().getPassword())
                .profile(pegawai.getUser().getProfile())
                .photo(pegawai.getPhoto())
                .build();
    }

    @PostMapping(path = "/ubah-photo")
    public String ubahPassword(@RequestBody Map<String, Object> request) {
        if(request.get("error") != null) {
            throw new BadRequest("Bad request");
        }
        return "ok";
    }

}
