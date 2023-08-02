package com.test.absensi.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("SELECT d FROM Department d WHERE d.name=:name")
    Optional<Department> findByName(String name);

    @Query(value = """
        SELECT new Department(d.id, d.name) FROM Department d 
        INNER JOIN Employee employee ON d.id = employee.department.id 
        INNER JOIN Company company ON company.id = employee.company.id 
        WHERE company.companyName=:companyName GROUP BY d.id, d.name
    """)
    List<Department> findAllByCompanyName(String companyName);
}
