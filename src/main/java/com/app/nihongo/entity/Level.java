package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Levels")
public class Level {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer levelId;
    private String levelName;
    private Integer displayOrder;
    @ManyToMany(mappedBy = "levels")
    private List<User> users = new ArrayList<>();
}
