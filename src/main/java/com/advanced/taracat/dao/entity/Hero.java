package com.advanced.taracat.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int race;
    private int heroClass;
    private int lidership;
    private int steps;
    private int abilities;
    private int items;
    private int expirience;
    private int leftExpirience;
    private int level;
    private int health;
    private int armor;
    private int immunitySlot;
    private int durability;
    private int weaponType;
    private int accuracy;
    private int damage;
    private int attackType;
    private int initiative;
    private int numberOfGoals;
    private boolean active;
    private int zoneX;
    private int zoneY;
    private int zoneInsideX;
    private int zoneInsideY;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
