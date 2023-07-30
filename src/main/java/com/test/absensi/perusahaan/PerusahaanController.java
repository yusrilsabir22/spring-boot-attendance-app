package com.test.absensi.perusahaan;

import com.test.absensi.perusahaan.PerusahaanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class PerusahaanController {

    private final PerusahaanService perusahaanService;

    @GetMapping(path = "/perusahaan")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(perusahaanService.getAllPerusahaan());
    }

}
