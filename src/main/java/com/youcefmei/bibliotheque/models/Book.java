package com.youcefmei.bibliotheque.models;

public class Book {
    private String title;
    private String author;
    private int quantity;

    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return  title + " - " + author ;
    }




}
