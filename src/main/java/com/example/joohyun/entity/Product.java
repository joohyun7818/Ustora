package com.example.joohyun.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pid;

    @Column
    private String pname;

    @Column
    private String author;

    @Column
    private int price;

    @Column
    private String brand;

    @Column
    private String category1;

    @Column
    private String category2;
}
