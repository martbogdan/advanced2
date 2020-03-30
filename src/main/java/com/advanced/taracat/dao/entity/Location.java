package com.advanced.taracat.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ІД локації, для бази
    private int zoneIdX; // ІД зони в якій знаходиться локація по X
    private int zoneIdY; // ІД зони в якій знаходиться локація по Y
    private String name; // Назва локації (Н-д: Замок або Печера Дракона)
    private int sizeWidth; // Ширина локації( наприклад 7 клітинок в ширину )
    private int sizeHeight; // Висота локації( наприклад 10 клітинок в висоту )
    private String locationImg; // Картинка локації
    private int locationOnOff;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
