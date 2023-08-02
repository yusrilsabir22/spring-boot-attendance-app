package com.test.absensi.attendance;

import com.test.absensi.employee.Employee;
import com.test.absensi.employee.EmployeeRepository;
import com.test.absensi.attendance.models.*;
import com.test.absensi.attendance_status.AttendanceStatus;
import com.test.absensi.attendance_status.AttendanceStatusRepository;
import com.test.absensi.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;

    private final AttendanceStatusRepository attendanceStatusRepository;

    private final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

    public boolean checkIn(){
        var employee = Utils.getUserSession();
        var checkIn = new Date(System.currentTimeMillis());

        var absentAttendance = Utils.DEFAULT_STATUS_ABSENSI.PRESENT.getOrdinal();
        Optional<AttendanceStatus> attendanceStatus = attendanceStatusRepository.findById(absentAttendance);

        if(attendanceStatus.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("Status absentAttendance tidak di dukung");
            logger.info("Status absentAttendance tidak di didukung");
            return false;
        }

        Optional<Attendance> latestAttendance = attendanceRepository.findLatestEntry(employee);
        if(latestAttendance.isPresent()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("attendance terakhir tidak ditemukan");
//            logger.info("attendance terakhir ditemukan, silahakan check-out attendance terakhir");
            logger.info("last attendance was found, please check-out first");
            return false;
        }

        Attendance attendance = Attendance.builder()
                .checkIn(checkIn)
                .employee(employee)
                .attendanceStatus(attendanceStatus.get())
                .build();
        attendanceRepository.save(attendance);
        return true;
    }

    public boolean checkOut() {
        var userSession = Utils.getUserSession();
        var employee = Utils.getUserSession();
        var checkOut = new Date(System.currentTimeMillis());

        var kdStatusAbsensi = Utils.DEFAULT_STATUS_ABSENSI.PRESENT.getOrdinal();
        Optional<AttendanceStatus> statusAbsensi = attendanceStatusRepository.findById(kdStatusAbsensi);

        if(statusAbsensi.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("Status absen tidak di dukung");
            logger.info("Status absen tidak di dukung");
            return false;
        }

        Optional<Attendance> latestAttendance = attendanceRepository.findLatestEntry(employee);
        if(latestAttendance.isEmpty()) {
            // Send logger to some logging service or throw error to send to the user-client
            // throw new BadRequest("latestAttendance terakhir tidak ditemukan");
            logger.info("last attendance was not found, please check-in first");
            return false;
        }
        Attendance updateAttendance = latestAttendance.get();
        updateAttendance.setCheckOut(checkOut);
        attendanceRepository.save(updateAttendance);
        return true;
    }

    public boolean abseni(AttendanceRequest request) {
        Employee employee = Utils.getUserSession();

        Attendance attendance = Attendance.builder()
                .absenceDate(request.getTglAbsensi())
                .employee(employee)
                .attendanceStatus(attendanceStatusRepository.getReferenceById(request.getKdStatus()))
                .build();
        attendanceRepository.save(attendance);
        return true;
    }

    public List<UserListAttendanceResponse> getAllAttendance() {
        var employee = Utils.getUserSession();
        return attendanceRepository.findAllByPegawai(employee);
    }

    public List<AdminListAttendanceiResponse> daftarPresensiAdmin(AttendanceRangeDateRequest request) {
        var userSession = Utils.getUserSession();
        var perusahaan = userSession.getCompany();
        return attendanceRepository.findAllByRangeDate(request.getTglAwal(), request.getTglAkhir(), perusahaan);
    }
}
