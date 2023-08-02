package com.test.absensi.work_unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkUnitRepository extends JpaRepository<WorkUnit, Integer> {

    @Query("SELECT u FROM WorkUnit u WHERE u.name=:name")
    public Optional<WorkUnit> findByName(String name);

    @Query(value = """
        SELECT new WorkUnit(u.id, u.name) FROM WorkUnit u 
        INNER JOIN Employee employee ON u.id = employee.workUnit.id
        INNER JOIN Company company ON company.id = employee.company.id 
        WHERE company.companyName=:name GROUP BY u.id, u.name
    """)
    List<WorkUnit> findAllByCompanyName(String name);
}
