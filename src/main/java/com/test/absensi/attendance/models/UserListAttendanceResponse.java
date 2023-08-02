package com.test.absensi.attendance.models;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
public class UserListAttendanceResponse {
    private int absenceDate;
    @Nullable
    private Integer checkIn;
    @Nullable
    private Integer checkOut;
    private String namaStatus;

    public UserListAttendanceResponse(Date absenceDate, @Nullable Date checkIn, @Nullable Date checkOut, String name) {
        this.absenceDate = (int) TimeUnit.MILLISECONDS.toSeconds(absenceDate.getTime());
        if(checkIn != null) {
            this.checkIn = (int) TimeUnit.MILLISECONDS.toSeconds(checkIn.getTime());
        }
        if(checkOut != null) {
            this.checkOut = (int) TimeUnit.MILLISECONDS.toSeconds(checkOut.getTime());
        }
        this.namaStatus = name;
    }
}
