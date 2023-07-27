package com.test.absensi.perusahaan;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerusahaanService {

    @Autowired
    private PerusahaanRepository perusahaanRepository;

    public Perusahaan initData(Perusahaan perusahaan) {
        return perusahaan;
    }

}
