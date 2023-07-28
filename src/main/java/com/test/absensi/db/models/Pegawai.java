package com.test.absensi.db.models;

import com.test.absensi.models.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "pegawai")
@AllArgsConstructor
@NoArgsConstructor
public class Pegawai {

    @Id
    @Column(name = "nik_user")
    private String nikUser;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "nama_lengkap")
    private String namaLengkap;

    @Column(name = "tempat_lahir")
    private String tempatLahir;

    @Column(name = "tanggal_lahir")
    private int tanggalLahir;

    @ManyToOne
    @JoinColumn(name = "kd_departemen")
    private Departemen departemen;

//    @Column(name="kd_jabatan")
//    private Integer kdJabatan;
//
//    @Column(name = "nama_jabatan")
//    private String namaJabatan;
//
//    @Column(name = "kd_unit_kerja")
//    private Integer kdUnitKerja;
//
//    @Column(name = "nama_unit_kerja")
//    private String namaUnitKerja;
//
//    @Column(name = "kd_jenis_kelamin")
//    private Integer kdJenisKelamin;
//
//    @Column(name = "nama_jenis_kelamin")
//    private String namaJenisKelamin;
//
//    @Column(name = "kd_pendidikan")
//    private Integer kdPendidikan;
//
//    @Column(name = "nama_pendidikan")
//    private String namaPendidikan;

    private String photo;

}
