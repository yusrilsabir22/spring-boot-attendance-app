package com.test.absensi.jabatan;

import com.test.absensi.departemen.Departemen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JabatanRepository extends JpaRepository<Jabatan, Integer> {

    @Query("SELECT d FROM Jabatan d WHERE d.namaJabatan=:namaJabatan")
    public Optional<Jabatan> findByNamaJabatan(String namaJabatan);

    @Query("SELECT d FROM Jabatan d WHERE d.kdJabatan=:kdJabatan")
    public Optional<Jabatan> findByKdJabatan(Integer kdJabatan);

    @Query(value = """
        SELECT new Jabatan(j.kdJabatan, j.namaJabatan) FROM Jabatan j 
        INNER JOIN Pegawai pegawai ON j.kdJabatan = pegawai.jabatan.kdJabatan 
        INNER JOIN User user ON user.email = pegawai.user.email 
        INNER JOIN Perusahaan perusahaan ON perusahaan.id = user.perusahaan.id 
        WHERE perusahaan.nama=:namaPerusahaan GROUP BY j.kdJabatan, j.namaJabatan
    """)
    List<Jabatan> findAllByNamaPerusahaan(String namaPerusahaan);

}
