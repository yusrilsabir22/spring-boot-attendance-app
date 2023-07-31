package com.test.absensi.departemen;

import com.test.absensi.pegawai.Pegawai;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartemenService {

    private final DepartemenRepository departemenRepository;

    public Optional<Departemen> getDepartemenHRD() {
        return departemenRepository.findByNamaDepartemen("HRD");
    }

}
