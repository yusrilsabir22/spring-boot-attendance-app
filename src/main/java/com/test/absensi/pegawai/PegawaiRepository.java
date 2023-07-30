package com.test.absensi.pegawai;

import com.test.absensi.perusahaan.Perusahaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PegawaiRepository extends JpaRepository<Pegawai, String> {

    @Query("SELECT p FROM Pegawai p WHERE p.user.perusahaan=:perusahaan")
    List<Pegawai> findAllByPerusahaan(Perusahaan perusahaan);

    @Query("SELECT p FROM Pegawai p WHERE p.nikUser=:nikUser")
    Optional<Pegawai> findOneByNikUser(String nikUser);

}
