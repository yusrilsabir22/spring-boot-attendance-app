package com.test.absensi.work_unit;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "work_unit")
@NoArgsConstructor
@Data
@Builder
public class WorkUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Getter
    private String name;

    public WorkUnit(String name) {
        this.name = name;
    }

    public WorkUnit(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
