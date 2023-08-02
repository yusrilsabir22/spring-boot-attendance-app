package com.test.absensi.attendance.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class AttendanceRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tglAbsensi;
    private int kdStatus;
}
