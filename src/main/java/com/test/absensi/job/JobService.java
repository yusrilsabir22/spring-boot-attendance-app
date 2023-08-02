package com.test.absensi.job;

import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public List<Job> getAll() {
        var userSession = Utils.getUserSession();
        return jobRepository.findAllByCompanyName(userSession.getCompany().getCompanyName());
    }

}
