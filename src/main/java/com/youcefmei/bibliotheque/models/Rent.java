package com.youcefmei.bibliotheque.models;

import java.util.Date;

public class Rent {
//    private long id;
    private Date dateBegin;
    private Date dateEnd;
    private Customer customer;
    private Book book;
    private Librarian librarian;


    public Rent(Date dateBegin, Customer customer, Book book, Librarian librarian) {
        this.dateBegin = dateBegin;
        this.dateEnd =  new Date( dateBegin.getTime() + 7 * 24 * 60 * 60 * 1000 );
        this.customer = customer;
        this.book = book;
        this.librarian = librarian;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Book getBook() {
        return book;
    }

    public Librarian getLibrarian() {
        return librarian;
    }


}
