package com.test.absensi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;

@Data
@Entity
@Table(name = "perusahaan")
public class Perusahaan {

    @Id
    @Getter
    private String perusahaan;

    public Perusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public Perusahaan() {}
}
