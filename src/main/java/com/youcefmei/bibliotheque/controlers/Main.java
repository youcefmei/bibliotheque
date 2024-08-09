package com.youcefmei.bibliotheque.controlers;

import com.youcefmei.bibliotheque.models.Librarian;
import com.youcefmei.bibliotheque.views.Menu;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Librarian li1 = new Librarian("rrr","tttt");
        Librarian li2 = new Librarian("pppp","wwww");
        Librarian li3 = new Librarian("nnnn","kkkk");


//        System.out.println(DateFormat.parse("2022/11/11"));
//        System.out.println(DateFormat.getDateInstance().format("2022/11/11"));
//        System.out.println(Calendar.getInstance().getTime());
//        Calendar cal = Calendar.getInstance();
//        cal.set(2022,11,11);
        LocalDate date = LocalDate.parse("2021-11-04");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRANCE);
        LocalDate dateTime = LocalDate.parse("5 juin 2022", formatter);
        dateTime = LocalDate.parse(" février 2022", formatter);

        System.out.println(dateTime);

        System.out.println(date);
        System.out.println(date.getDayOfMonth());
        System.out.println(date.getMonthValue());
        System.out.println(li1.getId());
        System.out.println(li2.getId());
        System.out.println(li3.getId());

        Menu menu = new Menu();

        menu.displayMenu();


    }
}