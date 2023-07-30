package com.test.absensi.pendidikan;

import com.test.absensi.departemen.Departemen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PendidikanRepository extends JpaRepository<Pendidikan, Integer> {

    @Query("SELECT d FROM Pendidikan d WHERE d.namaPendidikan=:namaPendidikan")
    public Optional<Pendidikan> findByNamaPendidikan(String namaPendidikan);

    @Query("SELECT d FROM Pendidikan d WHERE d.kdPendidikan=:kdPendidikan")
    public Optional<Pendidikan> findByKdPendidikan(Integer kdPendidikan);

    @Query(value = """
        SELECT new Pendidikan(p.kdPendidikan, p.namaPendidikan) FROM Pendidikan p 
        INNER JOIN Pegawai pegawai ON p.kdPendidikan = pegawai.pendidikan.kdPendidikan 
        INNER JOIN User user ON user.email = pegawai.user.email 
        INNER JOIN Perusahaan perusahaan ON perusahaan.id = user.perusahaan.id 
        WHERE perusahaan.nama=:namaPerusahaan GROUP BY p.kdPendidikan, p.namaPendidikan
    """)
    List<Pendidikan> findAllByNamaPerusahaan(String namaPerusahaan);

}
