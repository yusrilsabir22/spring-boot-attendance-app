package com.test.absensi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JenisKelamin {
    private int kdJenisKelamin;
    private String namaJenisKelamin;
}
