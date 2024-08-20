package com.youcefmei.bibliotheque.views.cli;

import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.exceptions.NotEnoughQuantityException;
import com.youcefmei.bibliotheque.models.Book;
import com.youcefmei.bibliotheque.models.Customer;
import com.youcefmei.bibliotheque.models.Librarian;
import com.youcefmei.bibliotheque.models.Rent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Menu {

    private Scanner scanner = new Scanner(System.in);

    public void displayMenu(){
        String choix;

        System.out.println("Veuillez faire un choix: ");

        System.out.println("1) Enregitrer un nouvel abonné");
        System.out.println("2) Enregitrer un nouveau livre");
        System.out.println("3) Enregitrer un nouveau prêt");
        System.out.println("4) Afficher la liste des abonnées");
        System.out.println("5) Afficher la liste des livres");
        System.out.println("6) Afficher la liste des prêts");
        System.out.println("7) Rechercher un livre");
        System.out.println("8) Rechercher un utilisateur");

        System.out.println("q pour quitter");


        choix = scanner.nextLine();
        switch(choix){
            case "1":
                newCustomerMenu();
                break;
            case "2":
                newBookMenu();
                break;
            case "3":
                newRentMenu();
                break;
            case "4":
                displayCustomer();
                break;
            case "5":
                displayBook();
                break;
            case "6":
                displayRent();
                break;
            case "7":
                searchBook();
                break;
            case "8":
                searchCustomer();
                break;
            case "q","Q":
                System.exit(0);
                break;
            default:
                System.out.println("Ce choix n'est pas valide");
                System.exit(0);
        }
        this.displayMenu();
    }

    private void newRentMenu()  {
        // Customer choice
        int choiceCustomer,choiceBook,choiceLibrarian;
        Customer customer = null;
        System.out.println("Qui est le client ?");
        List<Customer> customers = Library.getInstance().getCustomers();
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(i + " ) " + customers.get(i).getMail());
        }
        choiceCustomer = scanner.nextInt();
        if ((choiceCustomer >= 0) && (choiceCustomer < customers.size())) {
            customer = customers.get(choiceCustomer);
        } else {
            System.out.println("Ce choix n'est pas valide");
            newRentMenu();
        }
        // Book choice
        System.out.println("Quel est le livre à emprunter ?");
        List<Book> books = Library.getInstance().getBooks();
        List<Integer> booksNotEnough = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getQuantity() > 0) {
                System.out.println(i + " ) " + books.get(i).getTitle() + " de " + books.get(i).getAuthor());
            } else {
                booksNotEnough.add(i);
            }
        }
        choiceBook = scanner.nextInt();
        Book book = null;
        if ((choiceBook >= 0) && (choiceBook < books.size()) && (!booksNotEnough.contains(choiceBook))) {
            book = books.get(choiceBook);
        } else {
            System.out.println("Ce choix n'est pas valide");
            newRentMenu();
        }
        // librarian choice
        System.out.println("Qui est le/la libraire ?");
        List<Librarian> librarians = Library.getInstance().getLibrarians();
        for (int i = 0; i < librarians.size(); i++) {
            System.out.println(i + " ) " + librarians.get(i).getLastName() + " - " + librarians.get(i).getFirstName());
        }
        choiceLibrarian = scanner.nextInt();
        Librarian librarian = null;
        if ((choiceLibrarian >= 0) && (choiceLibrarian < librarians.size())) {
            librarian = librarians.get(choiceLibrarian);
        } else {
            System.out.println("Ce choix n'est pas valide");
            newRentMenu();
        }

        try {
            System.out.println(Library.getInstance().getBooks());
            Rent rent = new Rent(LocalDate.now(), customer, book, librarian);
            Library.getInstance().getRents().add(rent);
            System.out.println(Library.getInstance().getBooks());
            rent = new Rent(LocalDate.now(), customer, book, librarian);
            Library.getInstance().getRents().add(rent);
            System.out.println(Library.getInstance().getBooks());
        } catch (NotEnoughQuantityException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayCustomer() {
        List<Customer> customers = Library.getInstance().getCustomers();
        if (customers.size() > 0) {
            customers.forEach(customer -> System.out.println(customer.toString()));
        } else {
            System.out.println("Il n'y a pas d'utilisateur enregistré dans la librairie");
        }
    }

    private void displayBook() {
        List<Book> books = Library.getInstance().getBooks();
        if (books.size() > 0) {
            books.forEach(book -> System.out.println(book.toString()));
        } else {
            System.out.println("Il n'y a pas de livre dans la librairie");
        }

    }

    private void displayRent() {
        List<Rent> rents = Library.getInstance().getRents();
        if (rents.size() > 0) {
            rents.forEach(rent -> System.out.println(rent.toString()));
        }else {

            System.out.println("Il n'y a pas de prêt enregistré pour le moment");
        }
    }

    public void newCustomerMenu()  {
        String firstName, lastName,email,dateRegisterStr;
        System.out.println("Quel est le prénom ?");
        firstName = scanner.nextLine();
        System.out.println("Quel est le nom ?");
        lastName = scanner.nextLine();
        System.out.println("Quel est l'addresse mail ?");
        email = scanner.nextLine();
        System.out.println("Quel est la date d'inscription ? (ex: 31-12-2020) ");
        dateRegisterStr = scanner.nextLine();
        try {
            Customer customer1 = new Customer(firstName,lastName,email,dateRegisterStr);
            Library.getInstance().getCustomers().add(customer1);

        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            newCustomerMenu();
        }

    }


    public void newBookMenu()  {
        String title, author , quantityStr;
        int quantity;

        System.out.println("Quel est le titre du livre ?");
        title = scanner.nextLine();
        System.out.println("Qui est l'auteur ?");
        author = scanner.nextLine();
        System.out.println("Quel est la quantité ?");
        quantityStr = scanner.nextLine();

        try {
            quantity = Integer.parseInt(quantityStr);
            Library.getInstance().addBook(title, author, quantity);
        } catch (InvalidInputException | NumberFormatException e) {
            System.out.println(e.getMessage());
            newBookMenu();
        }

    }

    public void searchCustomer(){
        System.out.println("Quel est le nom de l'utilisateur ?");
        String toSearch = scanner.nextLine();
        List<Customer> customersFound = Library.getInstance().searchCustomer(toSearch);
        if (!customersFound.isEmpty()) {
            customersFound.forEach(System.out::println);

        }else{
            System.out.println("Il n'y pas d'utilisateur correspondant à cette recherche");
        }
    }



    public void searchBook(){
        System.out.println("Quel est le titre/auteur du livre ? ");
        String toSearch = scanner.nextLine();
        List<Book> booksFound = Library.getInstance().searchBook(toSearch);
        if (!booksFound.isEmpty()) {
            booksFound.forEach(System.out::println);
        }else{
            System.out.println("Il n'y pas de livre correspondant à cette recherche");
        }
    }



}
