package com.test.absensi.pegawai;

import com.test.absensi.departemen.Departemen;
import com.test.absensi.jabatan.Jabatan;
import com.test.absensi.models.TypeJenisKelamin;
import com.test.absensi.pendidikan.Pendidikan;
import com.test.absensi.unit_kerja.UnitKerja;
import com.test.absensi.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "pegawai")
@AllArgsConstructor
@NoArgsConstructor
public class Pegawai {

    @Id
    @Column(name = "nik_user")
    private String nikUser;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "nama_lengkap")
    private String namaLengkap;

    @Column(name = "tempat_lahir")
    private String tempatLahir;

    @Column(name = "tanggal_lahir")
    private Date tanggalLahir;

    @ManyToOne
    @JoinColumn(name = "kd_departemen")
    private Departemen departemen;

    @ManyToOne
    @JoinColumn(name="kd_jabatan")
    private Jabatan jabatan;

    @ManyToOne
    @JoinColumn(name = "kd_unit_kerja")
    private UnitKerja unitKerja;

    @Enumerated(EnumType.ORDINAL)
    private TypeJenisKelamin jenisKelamin;

    @ManyToOne
    @JoinColumn(name = "kd_pendidikan")
    private Pendidikan pendidikan;

    private String photo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

}
