package com.youcefmei.bibliotheque.models;

public class Librarian extends User{
    private static long total_id;
    private long id;

    public Librarian(String firstName, String lastName) {
        super(firstName, lastName);
        this.total_id++;
        this.id = total_id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "id=" + id +
                '}';
    }
}
