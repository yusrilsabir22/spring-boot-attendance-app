package com.test.absensi.pendidikan;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pendidikan")
@NoArgsConstructor
public class Pendidikan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "kd_pendidikan")
    @Getter
    private Integer kdPendidikan;

    @Column(name = "nama_pendidikan")
    @Getter
    private String namaPendidikan;

    public Pendidikan(String namaPendidikan) {
        this.namaPendidikan = namaPendidikan;
    }

    public Pendidikan(int kdPendidikan, String namaPendidikan) {
        this.kdPendidikan = kdPendidikan;
        this.namaPendidikan = namaPendidikan;
    }
}
