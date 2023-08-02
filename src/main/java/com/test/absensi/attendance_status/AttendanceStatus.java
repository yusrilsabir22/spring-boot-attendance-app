package com.test.absensi.attendance_status;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attendance_status")
@NoArgsConstructor
public class AttendanceStatus {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public AttendanceStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
