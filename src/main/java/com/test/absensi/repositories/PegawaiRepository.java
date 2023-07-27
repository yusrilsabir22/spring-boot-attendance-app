package com.test.absensi.repositories;

import com.test.absensi.models.Pegawai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PegawaiRepository extends JpaRepository<Pegawai, UUID> {



}
