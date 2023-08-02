package com.test.absensi.attendance.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AttendanceRangeDateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tglAwal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tglAkhir;
}
