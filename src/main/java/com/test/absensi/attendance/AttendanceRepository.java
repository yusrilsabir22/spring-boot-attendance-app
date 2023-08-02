package com.test.absensi.attendance;

import com.test.absensi.company.Company;
import com.test.absensi.employee.Employee;

import com.test.absensi.attendance.models.Attendance;
import com.test.absensi.attendance.models.AdminListAttendanceiResponse;
import com.test.absensi.attendance.models.UserListAttendanceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    @Query(value = """
        SELECT DISTINCT attendance FROM Attendance attendance
        INNER JOIN Employee employee ON attendance.employee.id = employee.id 
        WHERE attendance.employee=:employee 
        AND attendance.checkIn = NULL 
        AND attendance.checkOut IS NOT NULL 
        ORDER BY attendance.id, attendance.absenceDate DESC
    """)
    Optional<Attendance> findLatestEntry(Employee employee);
    @Query(value = """
        SELECT UserListAttendanceResponse(attendance.absenceDate, attendance.checkIn, attendance.checkOut, attendance.attendanceStatus.name) FROM Attendance attendance 
        WHERE attendance.employee=:employee ORDER BY attendance.id, attendance.absenceDate DESC 
    """)
    List<UserListAttendanceResponse> findAllByPegawai(Employee employee);
    @Query(value = """
        SELECT AdminListAttendanceiResponse(attendance.employee.nin, attendance.employee.fullname, attendance.absenceDate, attendance.checkIn, attendance.checkOut, attendance.attendanceStatus.name) FROM Attendance attendance  
        WHERE attendance.employee.company=:company
        AND attendance.absenceDate BETWEEN :startDate AND :endDate 
    """)
    List<AdminListAttendanceiResponse> findAllByRangeDate(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("company") Company company
    );
}
