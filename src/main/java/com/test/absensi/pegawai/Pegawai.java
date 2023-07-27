package com.test.absensi.pegawai;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pegawai")
public class Pegawai {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", insertable = false)
    private UUID id;

    private String profile;

    @Column(name = "nama_lengkap")
    private String namaLengkap;

    @Column(name = "tempat_lahir")
    private String tempatLahir;

    @Column(name = "tanggal_lahir")
    private int tanggalLahir;

    public Pegawai(String profile, String namaLengkap, String tempatLahir, int tanggalLahir) {
        this.profile = profile;
        this.namaLengkap = namaLengkap;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
    }

    public Pegawai() {}

    public UUID getId() {
        return id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public int getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(int tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
