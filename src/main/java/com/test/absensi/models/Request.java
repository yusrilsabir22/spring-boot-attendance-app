package com.test.absensi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Request {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InitData {
        private String namaAdmin;
        private String perusahaan;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        private String email;
        private String password;
        private Profile profile;
    }

}
