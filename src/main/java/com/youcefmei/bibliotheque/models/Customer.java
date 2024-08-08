package com.youcefmei.bibliotheque.models;

import java.util.Date;

public class Customer extends User {
    private String mail;
    private Date dateRegister;

    public Customer(String firstName, String lastName, String mail, Date dateRegister) {
        super(firstName, lastName);
        this.mail = mail;
        this.dateRegister = dateRegister;
    }
}
