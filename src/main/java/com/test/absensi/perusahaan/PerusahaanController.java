package com.test.absensi.perusahaan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "api")
public class PerusahaanController {

    @Autowired
    private PerusahaanService perusahaanService;

    @PostMapping(path = "/auth/init-data")
    public String initData(@RequestBody Map<String, Object> req) {
        String namaAdmin = (String) req.get("namaAdmin");
        String perusahaan = (String) req.get("perusahaan");
        System.out.println(namaAdmin);
        return "ok";
    }

}
