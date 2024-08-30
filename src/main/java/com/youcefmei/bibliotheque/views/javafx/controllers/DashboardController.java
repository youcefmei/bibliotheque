package com.youcefmei.bibliotheque.views.javafx.controllers;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Button dashboardCustomerButton,dashboardBookButton,dashboardRentButton,dashboardAddRentButton;

    @FXML
    private AnchorPane anchorPaneCustomer, anchorPaneBook,anchorPaneRent,anchorPaneLibrarian,anchorPaneAddRent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
//    private Event eventButton = new Event();

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        if(event.getSource()==dashboardRentButton){
            anchorPaneRent.setVisible(true);
            anchorPaneCustomer.setVisible(false);
            anchorPaneBook.setVisible(false);
            anchorPaneAddRent.setVisible(false);
            anchorPaneRent.fireEvent(new ActionEvent(RentController.class, new EventTarget() {
                @Override
                public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
                    return null;
                }
            }));
//            EventType eventType = new ;
//            Event event = new Event( );

//            anchorPaneRent.;
        }
        else if (event.getSource()==dashboardBookButton){
            anchorPaneBook.setVisible(true);
            anchorPaneRent.setVisible(false);
            anchorPaneCustomer.setVisible(false);
            anchorPaneAddRent.setVisible(false);
        }
        else if (event.getSource()==dashboardCustomerButton){
            anchorPaneCustomer.setVisible(true);
            anchorPaneBook.setVisible(false);
            anchorPaneRent.setVisible(false);
            anchorPaneAddRent.setVisible(false);
        }
        else if (event.getSource()==dashboardAddRentButton){
            anchorPaneAddRent.setVisible(true);
            anchorPaneCustomer.setVisible(false);
            anchorPaneBook.setVisible(false);
            anchorPaneRent.setVisible(false);
        }
        anchorPaneLibrarian.toFront();
    }



}
