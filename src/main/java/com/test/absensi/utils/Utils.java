package com.test.absensi.utils;

import com.test.absensi.attendance_status.AttendanceStatus;
import com.test.absensi.employee.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Utils {

    public static final String DEFAULT_DEPARTEMEN = "HRD";

    public static final String DEFAULT_JABATAN = "MANAJER";

    public static final String DEFAULT_PENDIDIKAN = "PENDIDIKAN";

    public static final String DEFAULT_UNIT_KERJA = "PRODUK-1";

    public static enum DEFAULT_STATUS_ABSENSI {
        PRESENT,
        ABSENT,
        SICK,
        PAID_LEAVE,
        LEAVE;
        public int getOrdinal() {
            return this.ordinal()+1;
        }
    }

//    @RequiredArgsConstructor
//    public static enum INITIAL_SERVICES {
//        DEPARTMENT(
//                Set.of(
//                        INITIAL_DATA_SERVICE.DEPARTMENT_HRD
//                )
//        ),
//        EDUCATION(
//                Set.of(
//                        INITIAL_DATA_SERVICE.EDUCATION_BACHELOR
//                )
//        ),
//        OFFICE(
//                Set.of(
//                        INITIAL_DATA_SERVICE.OFFICE_MANAGER
//                )
//        ),
//        ATTENDANCE(
//                Set.of(
//                        INITIAL_ATTENDANCE_STATUS.
//                )
//        ),
//        WORK_UNIT(
//                Set.of(INITIAL_DATA_SERVICE.WORK_UNIT_MAINTENANCE)
//        )
//        ;
//
//        @Getter
//        private final Set<INITIAL_DATA_SERVICE> initialDataServices;
//
//        public List<String> getInitialServices() {
//            var services = getInitialDataServices()
//                    .stream()
//                    .map(initial -> initial.getInitial_data_service())
//                    .collect(Collectors.toList());
//            return services;
//        }
//    }
//
//    @RequiredArgsConstructor
//    public static enum INITIAL_DATA_SERVICE {
//        DEPARTMENT_HRD("hrd"),
//        EDUCATION_BACHELOR("bachelor degree"),
//        OFFICE_MANAGER("manager"),
//        WORK_UNIT_MAINTENANCE("maintenance")
//        ;
//
//        @Getter
//        private final String initial_data_service;
//    }
//
//    @RequiredArgsConstructor
//    public static enum INITIAL_ATTENDANCE_STATUS {
//        PRESENT("present"),
//        ABSENT("absent"),
//        SICK("sick"),
//        PAID_LEAVE("paid leave"),
//        LEAVE("leave");
//
//        @Getter
//        private final String initial_attendance_status;
//
//        @Getter
//        private final Set<String> initialAttendanceStatuses() {
//            var statuses = getInitial_attendance_status()
//                    .lines()
//                    .collect(Collectors.toList());
//            return statuses;
//        }
//    }

    public static String randomPasswordGenerator(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String generateEmail(String nama, String perusahaan) {
        Random rnd = new Random();
        String namaPegawai = nama.replace(" ", "-").toLowerCase();
        String namaPerusahaan = perusahaan.replace(" ", "-").toLowerCase();
        return namaPegawai+rnd.nextInt(100)+"@"+namaPerusahaan+".com";
    }

    public static Employee getUserSession() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Employee) authentication.getPrincipal();
    }

    public static int dateToTimestamp(Date date) {
        long timeMilis = date.getTime();
        return (int) TimeUnit.MILLISECONDS.toSeconds(timeMilis);
    }

    public static Date timestampToDate(Integer timestamp) {
        return new Date(TimeUnit.SECONDS.toMillis(timestamp));
    }

    public static List<AttendanceStatus> getDefaultStatusAbsensi() {
        return Arrays.stream(DEFAULT_STATUS_ABSENSI.values())
                .map(defaultStatusAbsensi -> new AttendanceStatus(defaultStatusAbsensi.getOrdinal(), defaultStatusAbsensi.name()))
                .collect(Collectors.toList());
    }

    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try(
                FileOutputStream fos = new FileOutputStream( convFile ))
        {
            fos.write( file.getBytes() );
        }
        return convFile;
    }

    public static void log(Logger logger, String sender, String message) {
        logger.info(sender, message);
    }

}
