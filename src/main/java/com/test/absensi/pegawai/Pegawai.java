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

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "nik_user")
    private String nikUser;

    @Column(name = "kd_jabatan")
    private Integer kdJabatan;

    @Column(name = "nama_jabatan")
    private String namaJabatan;

    @Column(name = "kd_departemen")
    private Integer kdDepartemen;

    @Column(name = "nama_departemen")
    private String namaDepartemen;

    @Column(name = "kd_unit_kerja")
    private Integer kdUnitKerja;

    @Column(name = "nama_unit_kerja")
    private String namaUnitKerja;

    @Column(name = "kd_jenis_kelamin")
    private Integer kdJenisKelamin;

    @Column(name = "nama_jenis_kelamin")
    private String namaJenisKelamin;

    @Column(name = "kd_pendidikan")
    private Integer kdPendidikan;

    @Column(name = "nama_pendidikan")
    private String namaPendidikan;

    private String photo;


    public Pegawai(
            String profile,
            String namaLengkap,
            String tempatLahir,
            int tanggalLahir,
            String email,
            String password,
            String nikUser,
            Integer kdJabatan,
            String namaJabatan,
            Integer kdDepartemen,
            String namaDepartemen,
            Integer kdUnitKerja,
            String namaUnitKerja,
            Integer kdJenisKelamin,
            String namaJenisKelamin,
            Integer kdPendidikan,
            String namaPendidikan,
            String photo
    ) {
        this.profile = profile;
        this.namaLengkap = namaLengkap;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.email = email;
        this.password = password;
        this.nikUser = nikUser;
        this.kdJabatan = kdJabatan;
        this.namaJabatan = namaJabatan;
        this.kdDepartemen = kdDepartemen;
        this.namaDepartemen = namaDepartemen;
        this.kdUnitKerja = kdUnitKerja;
        this.namaUnitKerja = namaUnitKerja;
        this.kdJenisKelamin = kdJenisKelamin;
        this.namaJenisKelamin = namaJenisKelamin;
        this.kdPendidikan = kdPendidikan;
        this.namaPendidikan = namaPendidikan;
        this.photo = photo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNikUser() {
        return nikUser;
    }

    public void setNikUser(String nikUser) {
        this.nikUser = nikUser;
    }

    public Integer getKdJabatan() {
        return kdJabatan;
    }

    public void setKdJabatan(Integer kdJabatan) {
        this.kdJabatan = kdJabatan;
    }

    public String getNamaJabatan() {
        return namaJabatan;
    }

    public void setNamaJabatan(String namaJabatan) {
        this.namaJabatan = namaJabatan;
    }

    public Integer getKdDepartemen() {
        return kdDepartemen;
    }

    public void setKdDepartemen(Integer kdDepartemen) {
        this.kdDepartemen = kdDepartemen;
    }

    public String getNamaDepartemen() {
        return namaDepartemen;
    }

    public void setNamaDepartemen(String namaDepartemen) {
        this.namaDepartemen = namaDepartemen;
    }

    public Integer getKdUnitKerja() {
        return kdUnitKerja;
    }

    public void setKdUnitKerja(Integer kdUnitKerja) {
        this.kdUnitKerja = kdUnitKerja;
    }

    public String getNamaUnitKerja() {
        return namaUnitKerja;
    }

    public void setNamaUnitKerja(String namaUnitKerja) {
        this.namaUnitKerja = namaUnitKerja;
    }

    public Integer getKdJenisKelamin() {
        return kdJenisKelamin;
    }

    public void setKdJenisKelamin(Integer kdJenisKelamin) {
        this.kdJenisKelamin = kdJenisKelamin;
    }

    public String getNamaJenisKelamin() {
        return namaJenisKelamin;
    }

    public void setNamaJenisKelamin(String namaJenisKelamin) {
        this.namaJenisKelamin = namaJenisKelamin;
    }

    public Integer getKdPendidikan() {
        return kdPendidikan;
    }

    public void setKdPendidikan(Integer kdPendidikan) {
        this.kdPendidikan = kdPendidikan;
    }

    public String getNamaPendidikan() {
        return namaPendidikan;
    }

    public void setNamaPendidikan(String namaPendidikan) {
        this.namaPendidikan = namaPendidikan;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
