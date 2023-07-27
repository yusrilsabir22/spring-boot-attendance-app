package com.test.absensi.repositories;


import com.test.absensi.models.Perusahaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerusahaanRepository extends JpaRepository<Perusahaan, String> {
    @Query("SELECT p FROM Perusahaan p where p.perusahaan=:perusahaan")
    Optional<Perusahaan> findOne(String perusahaan);

}
