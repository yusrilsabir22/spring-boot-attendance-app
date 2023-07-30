package com.test.absensi.presensi;

import com.test.absensi.pegawai.Pegawai;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "presensi")
@NoArgsConstructor
public class Presensi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pegawai_id")
    private Pegawai pegawai;

    @Column(name = "tanggal_absensi")
    private Date tglAbsensi;

    @Column(name = "jam_masuk")
    private Date jamMasuk;

    @Column(name = "jam_keluar")
    private Date jamKeluar;


}
