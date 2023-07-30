package com.test.absensi.pegawai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPegawai {

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
}
