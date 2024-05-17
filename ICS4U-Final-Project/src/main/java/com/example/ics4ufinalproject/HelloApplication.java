package com.example.ics4ufinalproject;

import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;

public class HelloApplication extends Application {

    private TableView<Contact> table = new TableView<Contact>();
    private final ObservableList<Contact> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {

        final Label label = new Label("Contacts");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setPrefSize(720, 800);

        TableColumn<Contact, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().setFirstName(event.getNewValue());
                Contact.updateContacts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        TableColumn<Contact, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().setLastName(event.getNewValue());
                Contact.updateContacts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        TableColumn<Contact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().setEmail(event.getNewValue());
                Contact.updateContacts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        TableColumn<Contact, SimpleListProperty<String>> numberCol = new TableColumn<>("Numbers");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        numberCol.setCellFactory(column -> new TableCell<Contact, SimpleListProperty<String>>() {
            private final ListView<String> listView = new ListView<>();

            {
                listView.setEditable(true);
                listView.setCellFactory(TextFieldListCell.forListView());
                listView.setOnEditCommit(event -> {
                    int index = event.getIndex();
                    String newValue = event.getNewValue();
                    Contact contact = getTableView().getItems().get(getIndex());
                    contact.updateNumber(index, newValue);
                    listView.getItems().set(index, newValue);
                    try {
                        Contact.updateContacts();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(SimpleListProperty<String> numbers, boolean empty) {
                super.updateItem(numbers, empty);
                if (empty || numbers == null || numbers.isEmpty()) {
                    setText(null);
                    setGraphic(null);
                } else {
                    listView.setItems(numbers);
                    listView.setMaxHeight(60);
                    listView.setPrefHeight(60);
                    setGraphic(listView);
                }
            }
        });

        TableColumn<Contact, String> postalCodeCol = new TableColumn<>("Postal Code");
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("postalCode"));
        postalCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        postalCodeCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().setPostalCode(event.getNewValue());
                Contact.updateContacts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        TableColumn<Contact, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("company"));
        companyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        companyCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().setCompany(event.getNewValue());
                Contact.updateContacts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        firstNameCol.setMinWidth(120);
        firstNameCol.setMaxWidth(120);
        lastNameCol.setMinWidth(120);
        lastNameCol.setMaxWidth(120);
        emailCol.setMinWidth(120);
        emailCol.setMaxWidth(120);
        numberCol.setMinWidth(120);
        numberCol.setMaxWidth(120);
        postalCodeCol.setMinWidth(120);
        postalCodeCol.setMaxWidth(120);
        companyCol.setMinWidth(120);
        companyCol.setMaxWidth(120);

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, numberCol, postalCodeCol, companyCol);

        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 20, 10, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Labels
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(new Label("Number:"), 0, 3);
        grid.add(new Label("Postal Code:"), 0, 4);
        grid.add(new Label("Company:"), 0, 5);

        // TextFields
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField numberField = new TextField();
        TextField postalCodeField = new TextField();
        TextField companyField = new TextField();

        grid.add(firstNameField, 1, 0);
        grid.add(lastNameField, 1, 1);
        grid.add(emailField, 1, 2);
        grid.add(numberField, 1, 3);
        grid.add(postalCodeField, 1, 4);
        grid.add(companyField, 1, 5);

        Button add = new Button("Add");
        add.setPrefWidth(100); // Set preferred width
        add.setPrefHeight(50); // Set preferred height

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(firstNameField.getText().equals("")&&lastNameField.getText().equals("")&&emailField.getText().equals("")&&numberField.getText().equals("")&&postalCodeField.getText().equals("")&&companyField.getText().equals("")){

                }else{
                    Contact newcontact = new Contact(
                            firstNameField.getText(),
                            lastNameField.getText(),
                            emailField.getText(),
                            numberField.getText(),
                            postalCodeField.getText(),
                            companyField.getText()
                    );
                    data.add(newcontact);
                    Contact.contactList.add(newcontact);
                    firstNameField.clear();
                    lastNameField.clear();
                    emailField.clear();
                    numberField.clear();
                    postalCodeField.clear();
                    companyField.clear();

                    table.setItems(data);
                    try {
                        Contact.updateContacts();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        Button delete = new Button("Delete");
        delete.setPrefWidth(100); // Set preferred width
        delete.setPrefHeight(50); // Set preferred height

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Contact selectedContact = table.getSelectionModel().getSelectedItem();
                data.remove(selectedContact);
                Contact.contactList.remove(selectedContact);
                try {
                    Contact.updateContacts();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                table.setItems(data);
            }
        });

        final VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(add,delete);
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox2.setAlignment(Pos.CENTER);

        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox,vbox2, grid);

        Contact.readContacts();
        for(Contact e:Contact.contactList){
            data.add(e);
            table.setItems(data);
        }


        Scene scene = new Scene(hbox);
        stage.setTitle("Contacts");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}