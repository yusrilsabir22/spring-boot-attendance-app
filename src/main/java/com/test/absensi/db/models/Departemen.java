package com.test.absensi.db.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "departemen")
@AllArgsConstructor
@NoArgsConstructor
public class Departemen {

    @Id
    @Column(name = "kd_departemen")
    private Long kdDepartemen;

    @Column(name = "nama_departemen")
    private String namaDepartemen;

}
