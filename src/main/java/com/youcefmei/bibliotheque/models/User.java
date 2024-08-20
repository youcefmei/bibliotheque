package com.youcefmei.bibliotheque.models;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

@ToString
@Getter
public abstract class User {
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName) throws InvalidInputException {
        setLastName(lastName);
        setFirstName(firstName);
    }


    public void setFirstName(String firstName) throws InvalidInputException {
        if ( ( firstName != null ) && ( !firstName.isBlank() ) && ( firstName.matches(
                "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČ" +
                        "ŠŽ∂ð ,.'-]+$") )) {
            this.firstName = WordUtils.capitalize(firstName.toLowerCase().trim()) ;
        }else{
            throw  new InvalidInputException("Veuillez saisir un prénom valide !");
        }
    }

    public void setLastName(String lastName) throws InvalidInputException {
        if ( ( lastName != null ) && ( !lastName.isBlank() ) && ( lastName.matches(
                "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČ" +
                        "ŠŽ∂ð ,.'-]+$") )) {
            this.lastName = WordUtils.capitalize(lastName.toLowerCase().trim()) ;

        }else{
            throw  new InvalidInputException("Veuillez saisir un nom valide ! ");
        }
    }

}
