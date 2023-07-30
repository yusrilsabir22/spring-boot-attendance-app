package com.test.absensi.unit_kerja;

import com.test.absensi.departemen.Departemen;
import com.test.absensi.jabatan.Jabatan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitKerjaRepository extends JpaRepository<UnitKerja, Integer> {

    @Query("SELECT u FROM UnitKerja u WHERE u.namaUnitKerja=:namaUnitKerja")
    public Optional<UnitKerja> findByNamaUnitKerja(String namaUnitKerja);

    @Query("SELECT d FROM UnitKerja d WHERE d.kdUnitKerja=:kdUnitKerja")
    public Optional<UnitKerja> findByKdUnitKerja(Integer kdUnitKerja);

    @Query(value = """
        SELECT new UnitKerja(u.kdJabatan, u.namaJabatan) FROM Jabatan u 
        INNER JOIN Pegawai pegawai ON u.kdJabatan = pegawai.jabatan.kdJabatan 
        INNER JOIN User user ON user.email = pegawai.user.email 
        INNER JOIN Perusahaan perusahaan ON perusahaan.id = user.perusahaan.id 
        WHERE perusahaan.nama=:namaPerusahaan GROUP BY u.kdJabatan, u.namaJabatan
    """)
    List<UnitKerja> findAllByNamaPerusahaan(String namaPerusahaan);
}
