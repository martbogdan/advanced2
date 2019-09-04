package com.advanced.taracat.dao.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", length = 255)
    @Size(max = 255)
    private String userName;
    private String password;
    private int fights;
    private int winn;
    private int lose;
    @OneToMany
    private Cat cat;
}
