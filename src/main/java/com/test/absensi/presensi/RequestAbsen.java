package com.test.absensi.presensi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class RequestAbsen {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tglAbsensi;
    private int kdStatus;
}
