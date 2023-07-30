package com.test.absensi.presensi;

import com.test.absensi.pegawai.Pegawai;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
