package com.test.absensi.models;

import com.test.absensi.pegawai.Pegawai;
import com.test.absensi.user.Profile;
import com.test.absensi.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

        public static Response.User transform(Pegawai pegawai) {
            return Response.User.builder()
                    .nikUser(pegawai.getNikUser())
                    .namaLengkap(pegawai.getNamaLengkap())
                    .tempatLahir(pegawai.getTempatLahir())
                    .tanggalLahir(Utils.dateToTimestamp(pegawai.getTanggalLahir()))
                    .email(pegawai.getUser().getUsername())
                    .password(pegawai.getUser().getPassword())
                    .profile(pegawai.getUser().getProfile())
                    .kdDepartemen(pegawai.getDepartemen().getKdDepartemen())
                    .namaDepartemen(pegawai.getDepartemen().getNamaDepartemen())
                    .kdJabatan(pegawai.getJabatan().getKdJabatan())
                    .namaJabatan(pegawai.getJabatan().getNamaJabatan())
                    .kdPendidikan(pegawai.getPendidikan().getKdPendidikan())
                    .namaPendidikan(pegawai.getPendidikan().getNamaPendidikan())
                    .kdUnitKerja(pegawai.getUnitKerja().getKdUnitKerja())
                    .namaUnitKerja(pegawai.getUnitKerja().getNamaUnitKerja())
                    .kdJenisKelamin(pegawai.getJenisKelamin().getOrdinal())
                    .namaJenisKelamin(TypeJenisKelamin.values()[(pegawai.getJenisKelamin().getOrdinal() == 0 ? 0 : (pegawai.getJenisKelamin().getOrdinal() - 1))].name())
                    .photo(pegawai.getUser().getPhoto())
                    .createdAt(Utils.dateToTimestamp(pegawai.getCreatedAt()))
                    .updatedAt(Utils.dateToTimestamp(pegawai.getUpdatedAt()))
                    .build();
        }

        public static List<Response.User> toList(List<Pegawai> list) {
            return list.stream()
                    .map(Response.User::transform)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    public static class Error {
        int statusCode;
        String message;
    }

}
