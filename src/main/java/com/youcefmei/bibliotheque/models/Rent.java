package com.youcefmei.bibliotheque.models;

import java.util.Date;

public class Rent {
//    private long id;
    private Date dateBegin;
    private Date dateEnd;
    private Customer customer;
    private Book book;
    private Librarian librarian;

    public Rent(Date dateBegin, Date dateEnd, Customer customer, Book book, Librarian librarian) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.customer = customer;
        this.book = book;
        this.librarian = librarian;
    }
}
