package com.advanced.taracat.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int cat_head;
    private int cat_cheast;
    private int cat_belt;
    private int cat_legs;
    private int cat_straight;
    private int cat_hp;
    private int cat_level;
    private int cat_expirience;
    private int cat_maxexpirience;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
