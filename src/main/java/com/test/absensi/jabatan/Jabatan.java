package com.test.absensi.jabatan;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jabatan")
@NoArgsConstructor
public class Jabatan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "kd_jabatan")
    @Getter
    private Integer kdJabatan;

    @Column(name = "nama_jabatan")
    @Getter
    private String namaJabatan;

    public Jabatan(String namaJabatan) {
        this.namaJabatan = namaJabatan;
    }

    public Jabatan(int kdJabatan, String namaJabatan) {
        this.kdJabatan = kdJabatan;
        this.namaJabatan = namaJabatan;
    }
}
