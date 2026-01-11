package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "Units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unitId;

    private Integer displayOrder;
    private String name;
    private String description;
    private String level;

}
