package com.youcefmei.bibliotheque.models;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.exceptions.NotEnoughQuantityException;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

public class Rent {
    @Getter
    private LocalDate dateBegin;
    @Getter
    private LocalDate dateEnd;
    @Getter
    private Customer customer;
    private Book book;
    @Getter
    private Librarian librarian;
    private enum Status {
      BORROWED,
      RETURNED
    };
    private Status status;

    public Rent(LocalDate dateBegin, @NonNull Customer customer,@NonNull Book book,@NonNull Librarian librarian) throws NotEnoughQuantityException, InvalidInputException {
        if ( (book != null) && (book.getQuantity() < 1 )){
            throw new NotEnoughQuantityException("Il n y a pas assez d'exemplaire disponible");
        }else{
            book.bookBorrow();
            this.book = book;
            this.status = Status.BORROWED;
        }
        this.dateBegin = dateBegin;
        this.dateEnd =  dateBegin.plusDays(7);
//        this.dateEnd =  LocalDate.ofEpochDay( dateBegin.toEpochDay() + 7 * 24 * 60 * 60 * 1_000 );
        this.customer = customer;
        this.librarian = librarian;
    }

    public Book getBook()  {
        if ( (this.status == Status.BORROWED) && (  LocalDate.now().isAfter(this.getDateEnd())  )  ){
            this.status = Status.RETURNED;
            book.bookReturn();
        }
        return book;
    }
}
