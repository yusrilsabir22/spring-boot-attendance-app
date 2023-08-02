package com.test.absensi.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Query("SELECT d FROM Job d WHERE d.name=:name")
    public Optional<Job> findByName(String name);

    @Query(value = """
        SELECT new Job(j.id, j.name) FROM Job j 
        INNER JOIN Employee employee ON j.id = employee.job.id 
        INNER JOIN Company company ON company.id = employee.company.id 
        WHERE company.companyName=:name GROUP BY j.id, j.name
    """)
    List<Job> findAllByCompanyName(String name);

}
