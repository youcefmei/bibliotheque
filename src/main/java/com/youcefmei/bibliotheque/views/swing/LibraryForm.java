package com.youcefmei.bibliotheque.views.swing;
import com.youcefmei.bibliotheque.exceptions.DuplicateException;
import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LibraryForm extends JFrame implements ItemListener {
    //UI
    private JPanel contentPane;
    private JPanel menuPanel;
    private JComboBox menuComboBox;
    private JPanel mainPanel;
    private JPanel registerUserPanel;
    private JPanel registerBookPanel;
    private JPanel registerRentPanel;
    private JPanel listUserPanel;
    private JPanel listRentPanel;
    private JPanel listBookPanel;
    private JTextField lastnameUserRegisterTextField;
    private JTextField firstnameUserRegisterTextField;
    private JTextField emailUserRegisterTextField;
    private JButton validUserRegisterButton;
    private JButton clearUserRegisterButton;
    private JTextField textField4;
    private JTextField textField5;
    private JSpinner spinner1;
    private JButton effacerButton1;
    private JButton validerButton1;


    // Business logic
    private Library library = Library.getInstance();


    public LibraryForm()   {
        this.setTitle("Gestionnaire de Librairie ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set menu listener
        mainPanel.add(registerUserPanel,"Enregistrer un nouvel utilisateur");
        mainPanel.add(registerBookPanel,"Enregistrer un nouveau livre");
        mainPanel.add(registerRentPanel,"Enregistrer un nouveau pret");
        mainPanel.add(listBookPanel,"Liste des livres");
        mainPanel.add(listUserPanel,"Liste des utilisateurs");
        mainPanel.add(listRentPanel,"Liste des prets");
        menuComboBox.addItemListener(this);


        handleValidRegisterUser();
        handleClearRegisterUser();

        setContentPane(contentPane);
        pack();
        this.setVisible(true);


    }

    private void handleValidRegisterUser() {
        validUserRegisterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = firstnameUserRegisterTextField.getText();
                String lastname = lastnameUserRegisterTextField.getText();
                String email = emailUserRegisterTextField.getText();
                try {
                    Customer customer = new Customer(firstname,lastname,email) ;
                    library.addCustomer(customer);
                    JOptionPane.showMessageDialog(null, "L'utilisateur a été ajouté");
                } catch (InvalidInputException | DuplicateException ex) {
                JOptionPane.showMessageDialog(null , ex.getMessage(),"Attention",JOptionPane.WARNING_MESSAGE)  ;
                }
                System.out.println((library.getCustomers()));
            }
        });
    }


    private void handleClearRegisterUser() {
        clearUserRegisterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                firstnameUserRegisterTextField.setText("");
                lastnameUserRegisterTextField.setText("");
                emailUserRegisterTextField.setText("");
                System.out.println(("clearUserRegisterButton"));
            }
        });
    }



    @Override
    public void itemStateChanged(ItemEvent e) {
        // Change cardLayout based on menu selected
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, e.getItem().toString());
    }








}
