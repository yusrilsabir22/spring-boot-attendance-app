package com.test.absensi.perusahaan;

import jakarta.persistence.*;

@Entity
@Table
public class Perusahaan {

    @Id
    @SequenceGenerator(
            name = "perusahaan_sequence",
            sequenceName = "perusahaan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "perusahaan_sequence"
    )
    private Long id;

    @Column(name = "nama_perusahaan")
    private String namaPerusahaan;

    private String email;

    private String password;

    private String profile;

}
