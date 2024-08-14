package com.youcefmei.bibliotheque.controlers;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.models.Book;
import com.youcefmei.bibliotheque.models.Customer;
import com.youcefmei.bibliotheque.models.Librarian;
import com.youcefmei.bibliotheque.models.Rent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Library {
    private static Library INSTANCE;

    private List<Librarian> librarians = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rent> rents = new ArrayList<>();


    private Library() {
        initLibrarian();
        initCustomer();
        initBook();
    }

    public static Library getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Library();
        }
        return INSTANCE;
    }

    private void initBook()  {
        try {
            books.add(new Book("1984","george Orwell",5));
            books.add(new Book("la ferme des animaux","George Orwell",5));
            books.add(new Book("les misérables","victor hugo",2));
            books.add(new Book("le rouge et le noir","Stendhal",2));
            books.add(new Book("la peste","albert camus",2));
            books.add(new Book("ça","Stephen king",1));
            books.add(new Book("dune","Frank herbert",1));
            books.add(new Book("les trois mousquetaires","alexandre dumas",1));
            books.add(new Book("le comte de Monte-Cristo","alexandre Dumas",1));
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initLibrarian(){
        try {
            Librarian li1 = new Librarian("libone","aaaa");
            Librarian li2 = new Librarian("libtwo","bbbbb");
            Librarian li3 = new Librarian("libthree","ccccc");
            librarians.add(li1);
            librarians.add(li2);
            librarians.add(li3);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initCustomer(){
        try {
            Customer customer1 = new Customer("customerone","fnameone","customerone@gmaill.com","20-12-2017");
            Customer customer2 = new Customer("customertwo","fnametwo","customertwo@gmaill.com","02-02-2019");
            Customer customer3 = new Customer("customerthree","fnamethree","customerthree@gmaill.com","14-10-2022");
            customers.add(customer1);
            customers.add(customer2);
            customers.add(customer3);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }



}
