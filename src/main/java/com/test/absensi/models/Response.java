package com.test.absensi.models;

import com.test.absensi.user.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Response {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InitData {
        private String email;
        private String password;
        private Profile profile;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String nikUser;
        private String namaLengkap;
        private String tempatLahir;
        private Integer tanggalLahir;
        private String email;
        private String password;
        private Profile profile;
        private int kdDepartemen;
        private String namaDepartemen;
        private int kdPendidikan;
        private String namaPendidikan;
        private int kdJabatan;
        private String namaJabatan;
        private int kdUnitKerja;
        private String namaUnitKerja;
        private int kdJenisKelamin;
        private String namaJenisKelamin;
        private String photo;
        private int createdAt;
        private int updatedAt;
    }

    @Data
    @AllArgsConstructor
    public static class Error {
        int statusCode;
        String message;
    }

}
