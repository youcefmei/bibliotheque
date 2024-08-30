package com.youcefmei.bibliotheque.models;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.exceptions.NotEnoughQuantityException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public class Book {
    @Getter
    private String title;
    @Getter
    private String author;
    @Getter
    private int quantity;



    public Book(String title, String author, int quantity) throws InvalidInputException {
        setTitle(title);
        setAuthor(author);
        setQuantity(quantity);
    }

    public void setTitle(String title) throws InvalidInputException {
        if (title != null && !title.isEmpty()  && !title.trim().isBlank() ){
            this.title = StringUtils.capitalize(title.trim());
        }else{
            throw  new InvalidInputException("Veuillez saisir un titre valide ! ");
        }

    }

    public void setAuthor(String author) throws InvalidInputException {
        if ( ( author != null ) && ( author.matches(
                "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČ" +
                        "ŠŽ∂ð ,.'-]+$") )) {
            this.author = StringUtils.capitalize(author.trim()) ;

        }else{
            throw  new InvalidInputException("Veuillez saisir un nom d'auteur valide ! ");
        }
    }


    public void setQuantity(int quantity) throws InvalidInputException {
        if (quantity >= 0){
            this.quantity = quantity;
        }else{
            throw  new InvalidInputException("La quantité n'est pas valide");
        }
    }


    public void bookReturn() {
        this.quantity++;
    }

    public void bookBorrow() throws InvalidInputException, NotEnoughQuantityException {
        if ( getQuantity()>0 ){
            setQuantity( getQuantity() -1 );

        }else{
            throw new NotEnoughQuantityException("Ce livre n'est pas disponible pour le moment");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            return (this.getAuthor().equals(((Book) obj).getAuthor())) && (this.getTitle().equals(((Book) obj).getTitle()));
        }
        return false;
    }


    @Override
    public String toString() {
        return "Book{" +
                "title=" + getTitle()  + ", " +
                "author='" + getAuthor() + '\'' +
                ", quantity= " + getQuantity() +
                '}';

    }

}
