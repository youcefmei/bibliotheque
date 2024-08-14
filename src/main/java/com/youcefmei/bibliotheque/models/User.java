package com.youcefmei.bibliotheque.models;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

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
        if ( ( firstName != null ) && ( firstName.matches(
                "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČ" +
                        "ŠŽ∂ð ,.'-]+$") )) {
            this.firstName = StringUtils.capitalize(firstName.trim()) ;
        }else{
            throw  new InvalidInputException("Veuillez saisir un prénom valide !");
        }
    }

    public void setLastName(String lastName) throws InvalidInputException {
        if ( ( lastName != null ) && ( lastName.matches(
                "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČ" +
                        "ŠŽ∂ð ,.'-]+$") )) {
            this.lastName = StringUtils.capitalize(lastName.trim()) ;

        }else{
            throw  new InvalidInputException("Veuillez saisir un nom valide ! ");
        }
    }

}
