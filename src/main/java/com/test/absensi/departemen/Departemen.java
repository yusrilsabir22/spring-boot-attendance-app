package com.test.absensi.departemen;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "departemen")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Departemen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "kd_departemen")
    @Getter
    private Integer kdDepartemen;

    @Column(name = "nama_departemen")
    @Getter
    private String namaDepartemen;

    public Departemen(String namaDepartemen) {
        this.namaDepartemen = namaDepartemen;
    }

    public Departemen(int kdDepartemen, String namaDepartemen) {
        this.kdDepartemen = kdDepartemen;
        this.namaDepartemen = namaDepartemen;
    }
}
