package com.youcefmei.bibliotheque.controlers;

import com.youcefmei.bibliotheque.models.Librarian;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Librarian li1 = new Librarian("rrr","tttt");
        Librarian li2 = new Librarian("pppp","wwww");
        Librarian li3 = new Librarian("nnnn","kkkk");


        System.out.println(li1.getId());
        System.out.println(li2.getId());
        System.out.println(li3.getId());

    }
}