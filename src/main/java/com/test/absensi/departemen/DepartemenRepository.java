package com.test.absensi.departemen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartemenRepository extends JpaRepository<Departemen, Integer> {

    @Query("SELECT d FROM Departemen d WHERE d.namaDepartemen=:namaDepartemen")
    Optional<Departemen> findByNamaDepartemen(String namaDepartemen);

    @Query("SELECT d FROM Departemen d WHERE d.kdDepartemen=:kdDepartemen")
    Optional<Departemen> findByKdDepartemen(Integer kdDepartemen);

    @Query(value = """
        SELECT new Departemen(d.kdDepartemen, d.namaDepartemen) FROM Departemen d 
        INNER JOIN Pegawai pegawai ON d.kdDepartemen = pegawai.departemen.kdDepartemen 
        INNER JOIN User user ON user.email = pegawai.user.email 
        INNER JOIN Perusahaan perusahaan ON perusahaan.id = user.perusahaan.id 
        WHERE perusahaan.nama=:namaPerusahaan GROUP BY d.kdDepartemen, d.namaDepartemen
    """)
    List<Departemen> findAllByNamaPerusahaan(String namaPerusahaan);
}
