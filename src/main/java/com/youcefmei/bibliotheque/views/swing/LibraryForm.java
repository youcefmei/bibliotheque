package com.youcefmei.bibliotheque.views.swing;

import com.youcefmei.bibliotheque.exceptions.DuplicateException;
import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.exceptions.NotEnoughQuantityException;
import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Book;
import com.youcefmei.bibliotheque.models.Customer;
import com.youcefmei.bibliotheque.models.Librarian;
import com.youcefmei.bibliotheque.models.Rent;
import com.youcefmei.bibliotheque.views.swing.tablemodel.BookListTableModel;
import com.youcefmei.bibliotheque.views.swing.tablemodel.BookRentTableModel;
import com.youcefmei.bibliotheque.views.swing.tablemodel.RentTableModel;
import com.youcefmei.bibliotheque.views.swing.tablemodel.UserTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

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
    private JTextField titleBookRegisterTextField;
    private JTextField authorBookRegisterTextField;
    private JSpinner quantityBookRegisterSpinner;
    private JButton clearBookRegisterButton;
    private JButton validBookRegisterButton;
    private JTextField searchListBookTextField;
    private JComboBox selectSearchListBookCombo;
    private JTable tableListBookTable;
    private JTextField searchListUserTextField;
    private JComboBox selectSearchListUserCombo;
    private JTable tableListUserTable;
    private JTable tableListRentTable;
    private JTable tableRegisterRentTable;
    private JTextField searchUserMailRentRegisterTextField;
    private JComboBox comboUserMailRentRegisterComboBox;
    private JButton validRentRegisterButton;
    private JButton clearRentRegisterButton;
    private JComboBox comboLibrarianIdRentRegisterComboBox;


//    private Map<String,BookTableModel.FilterType> = new HashMap<>(){{

    // Business logic
    private Library library = Library.getInstance();


    public LibraryForm() {

        setContentPane(contentPane);
        this.setTitle("Gestionnaire de Librairie ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpinnerModel sm = new SpinnerNumberModel(1, 1, 50, 1);
        quantityBookRegisterSpinner.setModel(sm);


        populateListBookTable();
        populateListUserTable();
        populateListRentTable();
        populateBookListRegisterRentTable();

        populateComboUserMailRentRegister();
        populateComboLibrarianIdRentRegister();

        // Set menu listener
        mainPanel.add(registerUserPanel, "Enregistrer un nouvel utilisateur");
        mainPanel.add(registerBookPanel, "Enregistrer un nouveau livre");
        mainPanel.add(registerRentPanel, "Enregistrer un nouveau pret");
        mainPanel.add(listBookPanel, "Liste des livres");
        mainPanel.add(listUserPanel, "Liste des utilisateurs");
        mainPanel.add(listRentPanel, "Liste des prets");
        menuComboBox.addItemListener(this);

        // Panels handlers
        handleRegisterUser();
        handleRegisterBook();
        handleRegisterRent();
        handleListBook();
        handleListUser();
        handleRentRegister();


        this.setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }


    private void handleRegisterUser() {
        validUserRegisterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = firstnameUserRegisterTextField.getText();
                String lastname = lastnameUserRegisterTextField.getText();
                String email = emailUserRegisterTextField.getText();
                try {
                    Customer customer = new Customer(firstname, lastname, email);
                    library.addCustomer(customer);
                    JOptionPane.showMessageDialog(null, "L'utilisateur a été ajouté");
                    populateComboUserMailRentRegister();
                    populateListUserTable();
                } catch (InvalidInputException | DuplicateException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Attention", JOptionPane.WARNING_MESSAGE);
                }
//                System.out.println((library.getCustomers()));
            }
        });

        clearUserRegisterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                firstnameUserRegisterTextField.setText("");
                lastnameUserRegisterTextField.setText("");
                emailUserRegisterTextField.setText("");
            }
        });
    }


    private void handleRegisterBook() {
        validBookRegisterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleBookRegisterTextField.getText();
                String author = authorBookRegisterTextField.getText();
                Integer quantity = (Integer) quantityBookRegisterSpinner.getValue();
                try {
                    Book book = new Book(title, author, quantity);
                    library.addBook(book);
                    JOptionPane.showMessageDialog(null, "Le livre a été ajouté");
                    tableListBookTable.setModel(new BookListTableModel(searchListBookTextField.getText(), selectSearchListBookCombo.getSelectedItem().toString().toLowerCase()));
                    BookRentTableModel bookRentTableModel = new BookRentTableModel();
                    tableRegisterRentTable.setModel(bookRentTableModel);
                } catch (InvalidInputException | DuplicateException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Attention", JOptionPane.WARNING_MESSAGE);
                }
                System.out.println((library.getBooks()));
            }
        });
        clearBookRegisterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                titleBookRegisterTextField.setText("");
                authorBookRegisterTextField.setText("");
                quantityBookRegisterSpinner.setValue(1);
//                System.out.println(("clearUserRegisterButton"));
            }
        });
    }


    private void handleRegisterRent() {
        searchUserMailRentRegisterTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                populateComboUserMailRentRegister();
            }
        });
    }

    public void handleListBook() {
        searchListBookTextField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {

                populateListBookTable();
            }
        });

        selectSearchListBookCombo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    populateListBookTable();
                }
            }

        );
    }

    public void handleListUser() {
        searchListUserTextField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

                populateListUserTable();
            }


        });

        selectSearchListUserCombo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    populateListUserTable();
                }
            }
        );
    }


    public void handleRentRegister() {
        validRentRegisterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                BookRentTableModel bookRegisterRentModel = (BookRentTableModel) tableRegisterRentTable.getModel();
                Customer customer = library.getCustomers().stream().filter(
                        customerTemp -> customerTemp.getMail().equals(
                                comboUserMailRentRegisterComboBox.getSelectedItem().toString()
                        )
                ).findFirst().orElse(null);

                Librarian librarian = library.getLibrarians().stream().filter(
                        librarianTemp -> librarianTemp.getId().equals(
                                comboLibrarianIdRentRegisterComboBox.getSelectedItem().toString()
                        )
                ).findFirst().orElse(null);

                bookRegisterRentModel.getBooksToRent().forEach(
                        book -> {
                            try {
                                Rent rent = new Rent(LocalDate.now(), customer, book, librarian);
                                library.getRents().add(rent);
                                JOptionPane.showMessageDialog(null, "Le prêt a été enregistré");
                                populateListRentTable();
                                populateListBookTable();
                                populateBookListRegisterRentTable();
                            } catch (NotEnoughQuantityException ex) {
                                throw new RuntimeException(ex);
                            } catch (InvalidInputException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                );
            }
        });
    }


    private void populateListBookTable() {
        BookListTableModel bookListTableModel = new BookListTableModel(searchListBookTextField.getText(), selectSearchListBookCombo.getSelectedItem().toString().toLowerCase());
        tableListBookTable.setModel(bookListTableModel);
    }

    private void populateListUserTable() {
        UserTableModel userTableModel = new UserTableModel(searchListUserTextField.getText(), selectSearchListUserCombo.getSelectedItem().toString().toLowerCase());
        tableListUserTable.setModel(userTableModel);
    }

    private void populateListRentTable() {
        RentTableModel rentTableModel = new RentTableModel();
        tableListRentTable.setModel(rentTableModel);
    }


    private void populateBookListRegisterRentTable() {
        BookRentTableModel bookRentTableModel = new BookRentTableModel();
        tableRegisterRentTable.setModel(bookRentTableModel);

    }

    private void populateComboUserMailRentRegister() {
        comboUserMailRentRegisterComboBox.removeAllItems();
        library.getCustomers().stream().filter(
                customer -> customer.getMail().toLowerCase().contains(searchUserMailRentRegisterTextField.getText().toLowerCase())
        ).forEach(
                customer -> comboUserMailRentRegisterComboBox.addItem(customer.getMail().toLowerCase())
        );
    }

    private void populateComboLibrarianIdRentRegister() {
        comboLibrarianIdRentRegisterComboBox.removeAllItems();
        library.getLibrarians().stream().forEach(
                librarian -> comboLibrarianIdRentRegisterComboBox.addItem(librarian.getId())
        );
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Change cardLayout based on menu selected
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, e.getItem().toString());
    }


}
