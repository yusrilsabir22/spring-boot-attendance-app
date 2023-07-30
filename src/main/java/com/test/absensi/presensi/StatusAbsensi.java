package com.test.absensi.presensi;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table("status_absensi")
@NoArgsConstructor
public class StatusAbsensi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "kd_status")
    private int kdStatus;

    @Column(name = "nama_status")
    private String namaStatus;

}
