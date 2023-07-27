package com.test.absensi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitDataRequest {

    private String namaAdmin;
    private String perusahaan;

}
