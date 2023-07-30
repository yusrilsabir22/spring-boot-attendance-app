package com.test.absensi.presensi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusAbsensiRepository extends JpaRepository<StatusAbsensi, Integer> {

}
