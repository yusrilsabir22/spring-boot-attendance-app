package com.test.absensi.company;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "admin_name")
    private String adminName;

    public Company(String companyName, String adminName) {
        this.companyName = companyName;
        this.adminName = adminName;
    }

}
