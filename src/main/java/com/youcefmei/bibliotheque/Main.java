package com.youcefmei.bibliotheque;

import com.formdev.flatlaf.*;
import com.youcefmei.bibliotheque.views.cli.Menu;
import com.youcefmei.bibliotheque.views.swing.LibraryForm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main extends Application {



    public static void main(String[] args) throws IOException {
        Main app = new Main();
        String appConfigPath = Main.class.getClassLoader().getResource("app.properties").getPath()  ;
        System.out.println(appConfigPath);
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        String uiType = appProps.getProperty("uiType");
        String swingTheme = appProps.getProperty("swingTheme");





        switch (uiType) {
            case "cli":
                app.cli();
                break;
            case "swing":
                app.swingGui(swingTheme);
                break;
            case "javafx":
                launch();

//                app.javaFxGui();
                break;
            default:
                app.swingGui(swingTheme);
                break;
        }

    }




    private void javaFxGui(Stage stage) throws IOException {


    }


    public void cli(){

         Menu menu = new Menu();
         menu.displayMenu();
    }

    public void swingGui(String theme){
//        Flat
//        FlatDarculaLaf.setup();
        switch (theme){
            case "flatdarklaf":
                FlatDarkLaf.setup();
                break;
            case "flatdarculalaf":
                FlatDarculaLaf.setup();
                break;
            case "flatlightlaf":
                FlatLightLaf.setup();
                break;
            case "flatintellijlaf":
                FlatIntelliJLaf.setup();
            default:
                break;
        }

//        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LibraryForm libraryForm = new LibraryForm();
                libraryForm.setVisible(true);
            }

        });
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/MainView.fxml")));
        Scene scene = new Scene(root);

        stage.setTitle("Hanoi");
        stage.setScene(scene);
        stage.show();

    }
}