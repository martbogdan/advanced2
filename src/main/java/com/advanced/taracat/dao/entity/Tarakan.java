package com.advanced.taracat.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Tarakan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tarname;
    private Integer level;
    private Integer experience;
    private Integer step;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

 //   int level; // рівень таракана, який росте від к-сті перемог
 //   int expirience; // дається доствід за перемогу, тобто: з 1-ого лвла до 2-ого треба 500 досвіду, тобто 5 перемог, або дослів за перемогу може даватись рандомно, від 50 до 100
 //   int step; // це крок прижку таракана по шкалі, стартовий 1-3, як тільки таракан получає 2-й лвл, то його крок стає 1-4 тобто формула: 1-3+lvl
}
