package com.test.absensi.status_absensi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "status_absensi")
@NoArgsConstructor
public class StatusAbsensi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "kd_status")
    private int kdStatus;

    @Column(name = "nama_status")
    private String namaStatus;

    public StatusAbsensi(int kdStatus, String namaStatus) {
        this.kdStatus = kdStatus;
        this.namaStatus = namaStatus;
    }
}
