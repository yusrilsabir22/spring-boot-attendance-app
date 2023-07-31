package com.test.absensi.presensi;

import com.test.absensi.pegawai.Pegawai;

import com.test.absensi.perusahaan.Perusahaan;
import com.test.absensi.presensi.models.DaftarAdminPresensiResponse;
import com.test.absensi.presensi.models.DaftarPresensiResponse;
import com.test.absensi.presensi.models.Presensi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PresensiRepository extends JpaRepository<Presensi, Integer> {
    @Query(value = """
        SELECT DISTINCT presensi FROM Presensi presensi
        INNER JOIN Pegawai pegawai ON presensi.pegawai.nikUser = pegawai.nikUser 
        WHERE presensi.pegawai=:pegawai 
        AND presensi.jamKeluar = NULL 
        AND presensi.jamMasuk IS NOT NULL 
        ORDER BY presensi.id, presensi.tglAbsensi DESC
    """)
    Optional<Presensi> findLatestEntry(Pegawai pegawai);
    @Query(value = """
        SELECT new com.test.absensi.presensi.models.DaftarPresensiResponse(presensi.tglAbsensi, presensi.jamMasuk, presensi.jamKeluar, presensi.statusAbsensi.namaStatus) FROM Presensi presensi 
        WHERE presensi.pegawai=:pegawai ORDER BY presensi.id, presensi.jamKeluar DESC 
    """)
    List<DaftarPresensiResponse> findAllByPegawai(Pegawai pegawai);
    @Query(value = """
        SELECT new com.test.absensi.presensi.models.DaftarAdminPresensiResponse(presensi.pegawai.nikUser, presensi.pegawai.namaLengkap, presensi.tglAbsensi, presensi.jamMasuk, presensi.jamKeluar, presensi.statusAbsensi.namaStatus) FROM Presensi presensi  
        WHERE presensi.pegawai.user.perusahaan=:perusahaan
        AND presensi.tglAbsensi BETWEEN :startDate AND :endDate 
    """)
    List<DaftarAdminPresensiResponse> findAllByRangeDate(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("perusahaan") Perusahaan perusahaan
    );
}
