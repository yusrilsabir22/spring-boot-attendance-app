package com.test.absensi.attendance.models;

import com.test.absensi.employee.Employee;
import com.test.absensi.attendance_status.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/*
    Relationship between Employee and Status Absensi e.g. PegawaiStatusAbsensi
 */
@Entity
@Table(name = "attendance")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "attendance_status_id")
    private AttendanceStatus attendanceStatus;

    @Column(name = "absence_date", updatable = false)
    @CreationTimestamp
    private Date absenceDate;

    @Column(name = "check_in")
    private Date checkIn;

    @Column(name = "check_out")
    private Date checkOut;
}
