package com.test.absensi.utils;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.test.absensi.exceptions.BadRequest;
import com.test.absensi.presensi.StatusAbsensi;
import com.test.absensi.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Utils {

    public static final String DEFAULT_DEPARTEMEN = "HRD";

    public static final String DEFAULT_JABATAN = "MANAJER";

    public static final String DEFAULT_PENDIDIKAN = "PENDIDIKAN";

    public static final String DEFAULT_UNIT_KERJA = "PRODUK-1";

    public static enum DEFAULT_STATUS_ABSENSI {
        HADIR,
        ABSEN,
        SAKIT,
        CUTI,
        IZIN;
        public int getOrdinal() {
            return this.ordinal()+1;
        }
    }

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

    public static User getUserSession() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public static int dateToTimestamp(Date date) {
        long timeMilis = date.getTime();
        return (int) TimeUnit.MILLISECONDS.toSeconds(timeMilis);
    }

    public static List<StatusAbsensi> getDefaultStatusAbsensi() {
        return Arrays.stream(DEFAULT_STATUS_ABSENSI.values())
                .map(defaultStatusAbsensi -> new StatusAbsensi(defaultStatusAbsensi.getOrdinal(), defaultStatusAbsensi.name()))
                .collect(Collectors.toList());
    }

}
