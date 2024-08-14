package com.youcefmei.bibliotheque.controlers;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.models.Book;
import com.youcefmei.bibliotheque.models.Customer;
import com.youcefmei.bibliotheque.models.Librarian;
import com.youcefmei.bibliotheque.views.Menu;

import java.util.ArrayList;
import java.util.List;


public class Main {



    public static void main(String[] args)  {
        Library library = Library.getInstance();

        try {
            library.getBooks().add(new Book("aaa","eeee",1));
            System.out.println(
                library.getBooks()
            );
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

        Menu menu = new Menu();
        menu.displayMenu();
    }
}