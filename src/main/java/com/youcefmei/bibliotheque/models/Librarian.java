package com.youcefmei.bibliotheque.models;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import lombok.Getter;

import java.util.UUID;

public class Librarian extends User{
    @Getter
    private String id;

    public Librarian(String firstName, String lastName) throws InvalidInputException {
        super(firstName, lastName);
        this.id = UUID.randomUUID().toString();
    }


    @Override
    public String toString() {
        return "Librarian{" +
                "id=" + id  + ", " +
                "firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }
}
