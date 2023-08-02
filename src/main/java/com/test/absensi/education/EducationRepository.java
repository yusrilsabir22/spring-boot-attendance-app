package com.test.absensi.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

    @Query("SELECT d FROM Education d WHERE d.name=:name")
    public Optional<Education> findByNamaPendidikan(String name);

    @Query(value = """
        SELECT new Education(p.id, p.name) FROM Education p 
        INNER JOIN Employee employee ON p.id = employee.education.id  
        INNER JOIN Company company ON company.id = employee.company.id 
        WHERE company.companyName=:name GROUP BY p.id, p.name
    """)
    List<Education> findAllByNamaPerusahaan(String name);

}
