package com.test.absensi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

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
        private String photo;
    }

    @Data
    @AllArgsConstructor
    public static class Error {
        int statusCode;
        String message;
    }

}
