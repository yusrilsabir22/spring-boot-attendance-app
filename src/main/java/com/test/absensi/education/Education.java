package com.test.absensi.education;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "education")
@NoArgsConstructor
@Data
@Builder
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter
    private Integer id;

    @Column(name = "name")
    @Getter
    private String name;

    public Education(String name) {
        this.name = name;
    }

    public Education(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
