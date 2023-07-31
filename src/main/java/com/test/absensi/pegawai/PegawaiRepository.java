package com.test.absensi.pegawai;

import com.test.absensi.departemen.Departemen;
import com.test.absensi.perusahaan.Perusahaan;
import com.test.absensi.user.Profile;
import com.test.absensi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PegawaiRepository extends JpaRepository<Pegawai, String> {

    @Query("SELECT p FROM Pegawai p WHERE p.user.perusahaan=:perusahaan")
    List<Pegawai> findAllByPerusahaan(Perusahaan perusahaan);

    @Query("SELECT p FROM Pegawai p WHERE p.nikUser=:nikUser AND p.user.perusahaan=:perusahaan")
    Optional<Pegawai> findOneByNikUser(String nikUser, Perusahaan perusahaan);

    @Query("SELECT p FROM Pegawai p WHERE p.user.email=:email AND p.user.perusahaan=:perusahaan")
    Optional<Pegawai> findPegawaiByUserEmail(String email, Perusahaan perusahaan);

    @Query("SELECT p FROM Pegawai p WHERE p.user.email=:email AND p.user.profile=:profile")
    Optional<Pegawai> findByEmailAndProfile(String email, Profile profile);

    @Query("SELECT p FROM Pegawai p WHERE p.departemen=:departemen AND p.user.perusahaan=:perusahaan")
    List<Pegawai> findAllByDepartemenAndPerusahaan(Departemen departemen, Perusahaan perusahaan);
}
