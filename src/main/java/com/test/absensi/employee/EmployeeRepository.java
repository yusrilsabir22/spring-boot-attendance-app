package com.test.absensi.employee;

import com.test.absensi.company.Company;
import com.test.absensi.department.Department;
import com.test.absensi.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT p FROM Employee p WHERE p.company=:company AND p.profile != com.test.absensi.user.Profile.ADMIN")
    List<Employee> findAllByCompany(Company company);

    @Query("SELECT p FROM Employee p WHERE p.nin=:nikUser AND p.company=:company")
    Optional<Employee> findByNIN(String nikUser, Company company);

    @Query("SELECT p FROM Employee p WHERE p.email=:email AND p.company=:company")
    Optional<Employee> findByEmailAndCompany(String email, Company company);

    @Query("SELECT p FROM Employee p WHERE p.email=:email")
    Optional<Employee> findByEmail(String email);

    @Query("SELECT p FROM Employee p WHERE p.email=:email AND p.profile=:profile")
    Optional<Employee> findByEmailAndProfile(String email, Profile profile);

    @Query("SELECT p FROM Employee p WHERE p.department=:department AND p.company=:company")
    List<Employee> findAllByDepartmentAndCompany(Department department, Company company);
}
