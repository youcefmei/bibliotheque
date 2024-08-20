package com.youcefmei.bibliotheque.models;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianTest {
    private Librarian lib;

    @BeforeEach
    void setUp() throws InvalidInputException {
        lib = new Librarian("libfirst","liblast");
    }

    @AfterEach
    void tearDown() {
    }


    @ParameterizedTest
    @CsvSource({"firstName,Firstname","first Name,First Name",})
    void setFirstNameCapitalizeTest(String firstName,String expected) throws InvalidInputException {
        lib.setFirstName(firstName);
        assertEquals(expected,lib.getFirstName());
    }


    @Test
    void setFirstNameNullTest() throws InvalidInputException {
        assertThrows(InvalidInputException.class, () -> lib.setFirstName(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"","   "})
    void setFirstNameEmptyTest(String firstName) throws InvalidInputException {
        assertThrows(InvalidInputException.class, () -> lib.setFirstName(firstName));
    }


    @ParameterizedTest
    @CsvSource({"lastName,Lastname","last Name,Last Name",})
    void setLastNameCapitalizeTest(String lastName,String expected) throws InvalidInputException {
        lib.setLastName(lastName);
        assertEquals(expected,lib.getLastName());
    }

    @Test
    void setLastNameNullTest() throws InvalidInputException {
        assertThrows(InvalidInputException.class, () -> lib.setLastName(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"","   ","\t","\n"})
    void setLastNameEmptyTest(String lastName) throws InvalidInputException {
        assertThrows(InvalidInputException.class, () -> lib.setFirstName(lastName));
    }




}