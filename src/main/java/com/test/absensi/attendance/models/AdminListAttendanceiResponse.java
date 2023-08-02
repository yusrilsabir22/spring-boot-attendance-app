package com.test.absensi.attendance.models;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
public class AdminListAttendanceiResponse {
    private String userId;

    private String fullname;

    private int absenceDate;
    @Nullable
    private Integer checkIn;
    @Nullable
    private Integer checkOut;
    private String status;

    public AdminListAttendanceiResponse(
            String userId,
            String fullname,
            Date absenceDate,
            @Nullable Date checkIn,
            @Nullable Date checkOut,
            String status
    ) {
        this.userId = userId;
        this.fullname = fullname;
        this.absenceDate = (int) TimeUnit.MILLISECONDS.toSeconds(absenceDate.getTime());
        if(checkIn != null) {
            this.checkIn = (int) TimeUnit.MILLISECONDS.toSeconds(checkIn.getTime());
        }
        if(checkOut != null) {
            this.checkOut = (int) TimeUnit.MILLISECONDS.toSeconds(checkOut.getTime());
        }
        this.status = status;
    }
}
