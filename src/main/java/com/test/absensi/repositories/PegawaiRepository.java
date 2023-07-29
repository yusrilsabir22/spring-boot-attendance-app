package com.test.absensi.repositories;

import com.test.absensi.db.models.Pegawai;
import com.test.absensi.db.models.Perusahaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PegawaiRepository extends JpaRepository<Pegawai, String> {

    @Query("SELECT p FROM Pegawai p WHERE p.perusahaan=:perusahaan")
    List<Pegawai> findAllByPerusahaan(Perusahaan perusahaan);

    @Query("SELECT p FROM Pegawai p WHERE p.nikUser=:nikUser")
    Optional<Pegawai> findOneByNikUser(String nikUser);

}
