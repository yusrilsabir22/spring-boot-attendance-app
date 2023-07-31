package com.test.absensi.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private Hasil hasil;

    @Data
    @Builder
    public static class Hasil {
        private String token;
        private Info info;
    }

    @Data
    @Builder
    public static class Info {
        private String profile;
        private String nikUser;
        private String namaLengkap;
        private String email;
        private String tempatLahir;
        private Integer tanggalLahir;
        private Integer kdJenisKelamin;
        private Integer kdPendidikan;
        private Integer kdJabatan;
        private Integer kdDepartemen;
        private Integer kdUnitKerja;
        private String password;
        private String photo;
    }
}
