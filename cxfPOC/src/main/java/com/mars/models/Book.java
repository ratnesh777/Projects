package com.mars.models;

import lombok.Data;

@Data
//@Table(name = "book")
public class Book {
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private Integer id;
    private String title;
    private String author;
    private String description;
}