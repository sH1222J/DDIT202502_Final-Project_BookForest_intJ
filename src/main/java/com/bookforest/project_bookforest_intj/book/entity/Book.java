package com.bookforest.project_bookforest_intj.book.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Book {


    private Long bookId;

    private String title;
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private Integer pages;
    private Integer price;
    private Integer salePrice;

    private String description;

    private String contents;

    private String coverImg;
    private String isbn;
    private String category;
}
