package com.youcefmei.bibliotheque.models;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.GenericValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer extends User {
    @Getter
    private String mail;
    @Getter @Setter
    private LocalDate dateRegister;
    @Getter
    private String dateRegisterStr;


    public Customer(String firstName, String lastName, String mail) throws InvalidInputException {
        super(firstName, lastName);
        setMail(mail);
        setDateRegister (LocalDate.now() );
    }


    public Customer(String firstName, String lastName, String mail, LocalDate dateRegister) throws InvalidInputException {
        super(firstName, lastName);
        setMail(mail);
        setDateRegister(dateRegister);
    }

    public Customer(String firstName, String lastName, String mail, String dateRegisterStr) throws InvalidInputException {
        super(firstName, lastName);
        setMail(mail);
        setDateRegisterStr(dateRegisterStr);

    }


    private void setDateRegisterStr(String dateRegisterStr) throws InvalidInputException {
        if (GenericValidator.isDate(dateRegisterStr,"dd-MM-yyyy",true)){
            this.dateRegisterStr = dateRegisterStr;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            this.setDateRegister( LocalDate.parse( dateRegisterStr, formatter) );
        }
        else{
            throw new InvalidInputException("La date d'inscription n'est pas valide");
        }

    }


    public void setMail(String mail) throws InvalidInputException {
        if (GenericValidator.isEmail(mail)){
            this.mail = mail;
        }else{
            throw new InvalidInputException("L'addresse email n'est pas valide");
        }
    }


    @Override
    public String toString() {
        return "Customer{" +
                "mail='" + mail + '\'' +
                ", firstname=" + getFirstName() +
                ", lastname=" + getLastName() +
                ", dateRegister=" + dateRegister +
                '}';
    }
}
