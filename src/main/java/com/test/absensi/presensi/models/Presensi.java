package com.test.absensi.presensi.models;

import com.test.absensi.pegawai.Pegawai;
import com.test.absensi.status_absensi.StatusAbsensi;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/*
    Relationship between Pegawai and Status Absensi e.g. PegawaiStatusAbsensi
 */
@Entity
@Table(name = "presensi")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Presensi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pegawai_id")
    private Pegawai pegawai;

    @ManyToOne
    @JoinColumn(name = "status_absensi_id")
    private StatusAbsensi statusAbsensi;

    @Column(name = "tanggal_absensi", updatable = false)
    @CreationTimestamp
    private Date tglAbsensi;

    @Column(name = "jam_masuk")
    private Date jamMasuk;

    @Column(name = "jam_keluar")
    @Setter
    private Date jamKeluar;
}
