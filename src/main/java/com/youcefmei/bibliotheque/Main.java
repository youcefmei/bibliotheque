package com.youcefmei.bibliotheque;

import com.youcefmei.bibliotheque.views.cli.Menu;
import com.youcefmei.bibliotheque.views.swing.LibraryForm;

import javax.swing.*;


public class Main {



    public static void main(String[] args)  {
        Main app = new Main();

//        app.cli();
        app.swingGui();

    }


    public void cli(){

         Menu menu = new Menu();
         menu.displayMenu();
    }

    public void swingGui(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LibraryForm libraryForm = new LibraryForm();
                libraryForm.setVisible(true);
            }

        });
    }

}