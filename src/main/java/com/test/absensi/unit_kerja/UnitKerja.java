package com.test.absensi.unit_kerja;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit_kerja")
@NoArgsConstructor
public class UnitKerja {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "kd_unit_kerja")
    @Getter
    private Integer kdUnitKerja;

    @Column(name = "nama_unit_kerja")
    @Getter
    private String namaUnitKerja;

    public UnitKerja(String namaUnitKerja) {
        this.namaUnitKerja = namaUnitKerja;
    }

    public UnitKerja(Integer kdUnitKerja, String namaUnitKerja) {
        this.kdUnitKerja = kdUnitKerja;
        this.namaUnitKerja = namaUnitKerja;
    }
}
