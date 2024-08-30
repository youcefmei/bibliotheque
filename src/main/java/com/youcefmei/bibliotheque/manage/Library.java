package com.youcefmei.bibliotheque.manage;

import com.youcefmei.bibliotheque.exceptions.DuplicateException;
import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.exceptions.NotEnoughQuantityException;
import com.youcefmei.bibliotheque.models.Book;
import com.youcefmei.bibliotheque.models.Customer;
import com.youcefmei.bibliotheque.models.Librarian;
import com.youcefmei.bibliotheque.models.Rent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class Library {
    private static Library INSTANCE;

    private List<Librarian> librarians = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rent> rents = new ArrayList<>();
    @Getter @Setter
    private Librarian currentLibrarian;
//    @Getter @Setter
//    private Librarian librarian;


    private Library() {
        initLibrarian();
        initCustomer();
        initBook();
        initRent();
        if (librarians.size() > 0){
            currentLibrarian = librarians.get(0);
        }
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
            Customer customer1 = new Customer("customerone","fnameone","customerone@gmail.com","20-12-2017");
            Customer customer2 = new Customer("customertwo","fnametwo","customertwo@gmail.com","02-02-2019");
            Customer customer3 = new Customer("customerthree","fnamethree","customerthree@gmail.com","14-10-2022");
            customers.add(customer1);
            customers.add(customer2);
            customers.add(customer3);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }


    public void initRent(){
        try {
            LocalDate dateBegin = LocalDate.now().plusDays(-1);
            Customer customer = customers.get(0);
            Librarian librarian = librarians.get(1);
            Book book = books.get(5);
            Rent rent = new Rent(dateBegin,customer,book,librarian);
            Rent rent2 = new Rent(dateBegin.plusDays(-10),customers.get(1),books.get(6),librarians.get(0));
            rents.add(rent);
            rents.add(rent2);
        } catch (NotEnoughQuantityException e) {
            System.out.println(e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Book> searchBook(String toSearch) {
        Stream<Book> bookStream = books.stream().filter(book -> book.getTitle().contains(toSearch));
        return bookStream.toList();
        // bookStream.forEach( book -> System.out.println(book.getTitle()));

    }

    public List<Customer> searchCustomer(String toSearch) {

        Stream<Customer> customerStream = customers.stream().filter(customer -> customer.getMail().contains(toSearch));
        return customerStream.toList();
    }


    public void addBook(Book book) throws DuplicateException {
        Stream<Book> bookStream = books.stream().filter(bookItem-> ( bookItem.getTitle().equals(book.getTitle()) && bookItem.getAuthor().equals(book.getAuthor()) )   );

        if (bookStream.count() != 0 ) {
            throw new DuplicateException("Ce livre est déja dans la bibliotheque");
        }else{
            books.add(book);
        }

    }

    public void addCustomer(Customer customer) throws DuplicateException {
        Stream<Customer> customerStream = customers.stream().filter(customerItem-> customerItem.getMail().equals(customer.getMail()) );

        if (customerStream.count() != 0 ) {
            throw new DuplicateException("Cette adresse mail est déja utilisé");
        }else{
            customers.add(customer);
        }
    }


}
