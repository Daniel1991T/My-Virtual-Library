package com.compani.ilai.myvirtuallibrary.services;

import androidx.annotation.Nullable;

public class Book {
    private int id;
    private String name;
    private String author;
    private int pages;
    private String gen;
    private String urlImage;
    private String shortDescription;
    private boolean isExpanded;

    public Book(int id, String name, String author, int pages, String gen, String urlImage, String shortDescription) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.gen = gen;
        this.urlImage = urlImage;
        this.shortDescription = shortDescription;
        this.isExpanded = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Book book = (Book) obj;
        return name.equals(book.name);
    }
}
