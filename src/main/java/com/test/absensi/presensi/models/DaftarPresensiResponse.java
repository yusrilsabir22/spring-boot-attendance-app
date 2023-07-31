package com.test.absensi.presensi.models;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
public class DaftarPresensiResponse {
    private int tglAbsensi;
    @Nullable
    private Integer jamMasuk;
    @Nullable
    private Integer jamKeluar;
    private String namaStatus;

    public DaftarPresensiResponse(Date tglAbsensi, @Nullable Date jamMasuk, @Nullable Date jamKeluar, String namaStatus) {
        this.tglAbsensi = (int) TimeUnit.MILLISECONDS.toSeconds(tglAbsensi.getTime());
        if(jamMasuk != null) {
            this.jamMasuk = (int) TimeUnit.MILLISECONDS.toSeconds(jamMasuk.getTime());
        }
        if(jamKeluar != null) {
            this.jamKeluar = (int) TimeUnit.MILLISECONDS.toSeconds(jamKeluar.getTime());
        }
        this.namaStatus = namaStatus;
    }
}
