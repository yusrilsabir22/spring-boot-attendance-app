package com.test.absensi.presensi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class RequestAbsensiRangeDate {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tglAwal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tglAkhir;
}
