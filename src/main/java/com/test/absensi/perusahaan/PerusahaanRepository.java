package com.test.absensi.perusahaan;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerusahaanRepository extends JpaRepository<Perusahaan, Long> {



}
