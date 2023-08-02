package com.test.absensi.employee;

import com.test.absensi.company.Company;
import com.test.absensi.department.Department;
import com.test.absensi.education.Education;
import com.test.absensi.job.Job;
import com.test.absensi.models.Sex;
import com.test.absensi.user.Profile;
import com.test.absensi.work_unit.WorkUnit;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements UserDetails {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    // NIN a.k.a. National Identity Number
    @Column(name = "nin", unique = true)
    private String nin;

    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @Column(nullable = false)
    private String password;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Nullable
    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Nullable
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @Nullable
    @ManyToOne
    @JoinColumn(name="job_id")
    private Job job;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "work_unit_id")
    private WorkUnit workUnit;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "edu_id")
    private Education education;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String photo;

    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private Profile profile;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profile.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
