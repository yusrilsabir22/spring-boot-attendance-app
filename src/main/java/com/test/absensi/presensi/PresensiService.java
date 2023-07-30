package com.test.absensi.presensi;

import com.test.absensi.exceptions.BadRequest;
import com.test.absensi.pegawai.Pegawai;
import com.test.absensi.pegawai.PegawaiRepository;
import com.test.absensi.user.Profile;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PresensiService {

    private final PresensiRepository presensiRepository;

    private final PegawaiRepository pegawaiRepository;

    private final StatusAbsensiRepository statusAbsensiRepository;

    public boolean checkIn(){
        var userSession = Utils.getUserSession();
        Optional<Pegawai> pegawai = pegawaiRepository.findPegawaiByUserEmail(userSession.getUsername());
        var waktuMasuk = new Date(System.currentTimeMillis());

        if(pegawai.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("pegawai tidak terdaftar");
            System.out.println("PresensiService: pegawai tidak terdaftar");
            return false;
        }
        var kdStatusAbsensi = Utils.DEFAULT_STATUS_ABSENSI.HADIR.getOrdinal();
        Optional<StatusAbsensi> statusAbsensi = statusAbsensiRepository.findById(kdStatusAbsensi);

        if(statusAbsensi.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("Status absen tidak di dukung");
            System.out.println("PresensiService: Status absen tidak di didukung");
            return false;
        }

        Optional<Presensi> currentPresensi = presensiRepository.findLatestEntry(pegawai.get());
        if(currentPresensi.isPresent()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("presensi terakhir tidak ditemukan");
            System.out.println("PresensiService: presensi terakhir ditemukan, silahakan check-out presensi terakhir");
            return false;
        }

        Presensi presensi = Presensi.builder()
                .jamMasuk(waktuMasuk)
                .pegawai(pegawai.get())
                .statusAbsensi(statusAbsensi.get())
                .build();
        presensiRepository.save(presensi);
        return true;
    }

    public boolean checkOut() {
        var userSession = Utils.getUserSession();
        Optional<Pegawai> pegawai = pegawaiRepository.findPegawaiByUserEmail(userSession.getUsername());
        var waktuKeluar = new Date(System.currentTimeMillis());

        if(pegawai.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("pegawai tidak terdaftar");
            System.out.println("PresensiService: pegawai tidak terdaftar");
            return false;
        }
        var kdStatusAbsensi = Utils.DEFAULT_STATUS_ABSENSI.HADIR.getOrdinal();
        Optional<StatusAbsensi> statusAbsensi = statusAbsensiRepository.findById(kdStatusAbsensi);

        if(statusAbsensi.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("Status absen tidak di dukung");
            System.out.println("PresensiService: Status absen tidak di dukung");
            return false;
        }

        Optional<Presensi> presensi = presensiRepository.findLatestEntry(pegawai.get());
        if(presensi.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("presensi terakhir tidak ditemukan");
            System.out.println("PresensiService: presensi terakhir tidak ditemukan");
            return false;
        }
        Presensi updatePresensi = presensi.get();
        updatePresensi.setJamKeluar(waktuKeluar);
        presensiRepository.save(updatePresensi);
        return true;
    }

    public boolean abseni(RequestAbsen request) {
        var userSession = Utils.getUserSession();
        Optional<Pegawai> pegawai = pegawaiRepository.findPegawaiByUserEmail(userSession.getUsername());

        if(pegawai.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("pegawai tidak terdaftar");
            System.out.println("PresensiService: pegawai tidak terdaftar");
            return false;
        }

        Optional<StatusAbsensi> statusAbsensi = statusAbsensiRepository.findById(request.getKdStatus());

        if(statusAbsensi.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("Status absen tidak di dukung");
            System.out.println("PresensiService: Status absen tidak di dukung");
            return false;
        }

        Presensi presensi = Presensi.builder()
                .tglAbsensi(request.getTglAbsensi())
                .pegawai(pegawai.get())
                .statusAbsensi(statusAbsensi.get())
                .build();
        presensiRepository.save(presensi);
        return true;
    }

    public List<DaftarPresensiResponse> daftarPresensi() {
        var userSession = Utils.getUserSession();
        Optional<Pegawai> pegawai = pegawaiRepository.findPegawaiByUserEmail(userSession.getUsername());
        if(pegawai.isEmpty()) {
            System.out.println("PresensiService-daftarPresensi: pegawai tidak ditemukan");
            return List.of();
        }
        return presensiRepository.findAllByPegawai(pegawai.get());
    }

    public List<DaftarAdminPresensiResponse> daftarPresensiAdmin(RequestAbsensiRangeDate request) {
        var userSession = Utils.getUserSession();
        var perusahaan = userSession.getPerusahaan();
        return presensiRepository.findAllByRangeDate(request.getTglAwal(), request.getTglAkhir(), perusahaan);
    }
}
