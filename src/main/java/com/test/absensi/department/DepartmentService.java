package com.test.absensi.department;

import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Optional<Department> getDepartementHRD() {
        return departmentRepository.findByName("HRD");
    }

    public List<Department> getAllBasedCompany() {
        var userSession = Utils.getUserSession();
        return departmentRepository.findAllByCompanyName(userSession.getCompany().getCompanyName());
    }


}
