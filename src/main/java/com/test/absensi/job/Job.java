package com.test.absensi.job;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job")
@NoArgsConstructor
@Data
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter
    private Integer id;

    @Column(name = "name")
    @Getter
    private String name;

    public Job(String name) {
        this.name = name;
    }

    public Job(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
