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
    private Integer running;
    private Integer win;
    private Integer loss;
    private Integer draw;
    private Integer wayForBot;
    private String imgId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
