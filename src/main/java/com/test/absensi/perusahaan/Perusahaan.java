package com.test.absensi.perusahaan;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;

@Data
@Entity
@Table(name = "perusahaan")
public class Perusahaan {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    private String nama;

    public Perusahaan(String nama) {
        this.nama = nama;
    }

    public Perusahaan() {}
}
