package com.youcefmei.bibliotheque.views;

import com.youcefmei.bibliotheque.models.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);

    public void displayMenu(){
        String choix;

        System.out.println("Veuillez faire un choix");

        System.out.println("1) Enregitrer un nouvel abonné");
        System.out.println("2) Enregitrer un nouveau livre");
        System.out.println("3) Enregitrer un nouveau prêt");
        System.out.println("4) Afficher la liste des abonnées");
        System.out.println("5) Afficher la liste des livres");
        System.out.println("6) Afficher la liste des prêts");
        System.out.println("q pour quitter");


        choix = scanner.nextLine();
        switch(choix){
            case "1":
                newCustomerMenu();
                break;
            case "2":

                break;
            case "3":

                break;
            case "4":

                break;
            case "5":

                break;
            case "6":

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

    public void newCustomerMenu(){
        String firstName, lastName,email;

        System.out.println("Quel est le prénom");


        List<Customer> customers = new ArrayList<Customer>();
        Customer customer1 = new Customer("fname1","lname1","zaere@tr",new Date(22,140,11));

        customers.add(customer1);

        System.out.println(customer1.toString());

    }








}
