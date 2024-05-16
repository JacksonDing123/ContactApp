package com.example.ics4ufinalproject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;

public class HelloApplication extends Application {

    private TableView<Contact> table = new TableView<Contact>();
    private final ObservableList<Object> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {

        final Label label = new Label("Contacts");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setPrefSize(720, 800);

        TableColumn<Contact, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));

        TableColumn<Contact, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));

        TableColumn<Contact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("email"));

        TableColumn<Contact, String> numberCol = new TableColumn<>("First Name");
        numberCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("number"));

        TableColumn<Contact, String> postalCodeCol = new TableColumn<>("Last Name");
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("postalCode"));

        TableColumn<Contact, String> companyCol = new TableColumn<>("Email");
        companyCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("company"));

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

        Button add = new Button("Add");
        add.setPrefWidth(100); // Set preferred width
        add.setPrefHeight(50); // Set preferred height
        Button delete = new Button("Delete");
        delete.setPrefWidth(100); // Set preferred width
        delete.setPrefHeight(50); // Set preferred height

        final VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(add,delete);
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox2.setAlignment(Pos.CENTER);

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

        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox,vbox2, grid);

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