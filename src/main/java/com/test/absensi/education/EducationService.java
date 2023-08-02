package com.test.absensi.education;

import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;

    public List<Education> getAll() {
        var userSession = Utils.getUserSession();
        return educationRepository.findAllByNamaPerusahaan(userSession.getCompany().getCompanyName());
    }

}
