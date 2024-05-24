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

    //creat table view and observable list to show the data
    private TableView<Contact> table = new TableView<Contact>();
    private final ObservableList<Contact> data = FXCollections.observableArrayList();
    //makes a list view for the dynamic property
    //private final ListView<String> listView = new ListView<>();

    @Override
    public void start(Stage stage) throws IOException {

        //creates second vbox for the add and delete buttons with proper spacing settings
        final VBox vbox2 = new VBox();

        //title for the contact app
        final Label label = new Label("Contacts");
        label.setFont(new Font("Arial", 20));

        //creates a Vbox for the table
        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        //creates a grid pane for the text field and the input values
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 20, 10, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        // adding labels into the grid with the names of what each text field represent
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(new Label("Number:"), 0, 3);
        grid.add(new Label("Postal Code:"), 0, 4);
        grid.add(new Label("Company:"), 0, 5);
        grid.add(new Label("New Number"), 0, 6);
        grid.add(new Label("Delete Number Index"), 0, 7);

        // creates textfields
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField numberField = new TextField();
        TextField postalCodeField = new TextField();
        TextField companyField = new TextField();
        TextField newNumber = new TextField();
        TextField deleteNumIndex = new TextField();

        //add the texfields into the grid
        grid.add(firstNameField, 1, 0);
        grid.add(lastNameField, 1, 1);
        grid.add(emailField, 1, 2);
        grid.add(numberField, 1, 3);
        grid.add(postalCodeField, 1, 4);
        grid.add(companyField, 1, 5);
        grid.add(newNumber, 1, 6);
        grid.add(deleteNumIndex, 1, 7);

        //creates the add button with set sizes
        Button add = new Button("Add");
        add.setPrefWidth(100); // Set preferred width
        add.setPrefHeight(50); // Set preferred height

        //below is what the program execute when add is pressed
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(firstNameField.getText().equals("")&&lastNameField.getText().equals("")&&emailField.getText().equals("")&&numberField.getText().equals("")&&postalCodeField.getText().equals("")&&companyField.getText().equals("")){
                    //if all of the fields are clear then do nothing
                }else{
                    //create a new contact from the given information given in the textfield
                    Contact newcontact = new Contact(
                            firstNameField.getText(),
                            lastNameField.getText(),
                            emailField.getText(),
                            numberField.getText(),
                            postalCodeField.getText(),
                            companyField.getText()
                    );
                    //add the new contact to the table and to the contactList in the contact class
                    data.add(newcontact);
                    Contact.contactList.add(newcontact);

                    //clear all of the fields for future use
                    firstNameField.clear();
                    lastNameField.clear();
                    emailField.clear();
                    numberField.clear();
                    postalCodeField.clear();
                    companyField.clear();

                    //updates the table with the new data
                    table.setItems(data);

                    //updates the contact list to write the new information into the json file
                    try {
                        Contact.updateContacts();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //creates the delete button with size
        Button delete = new Button("Delete");
        delete.setPrefWidth(100); // Set preferred width
        delete.setPrefHeight(50); // Set preferred height

        //below is what the program execute when add is pressed
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Contact selectedContact = table.getSelectionModel().getSelectedItem(); //finds the contact that is selected on the table
                data.remove(selectedContact); //remove the selected contact in the table view data
                Contact.contactList.remove(selectedContact); //remove the selected contact from the contactList in the contact class
                //updates this information into the json file after the removal of that contact
                try {
                    Contact.updateContacts();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //refresh the view of the table and show the table after the contact has been removed
                table.setItems(data);
            }
        });

        //make the table editable and set the size for the table
        table.setEditable(true);
        table.setPrefSize(720, 800);

        //below sets up all the colums with the right data structure and the right label for each colum
        TableColumn<Contact, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //set the on edit action so that when the value is edited, it changes the contact list in the contact clas and updates the information in the json file through the update method
        firstNameCol.setOnEditCommit(event -> {
            try {
                event.getRowValue().setFirstName(event.getNewValue());
                Contact.updateContacts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //the above two comments apply to all of the properties of a contact except the number property
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

        Button deleteNumber = new Button("Delete Number");
        vbox2.getChildren().add(deleteNumber);
        deleteNumber.setPrefWidth(100); // Set preferred width
        deleteNumber.setPrefHeight(50); // Set preferred height

        Button addNumber = new Button("Add Number");
        vbox2.getChildren().add(addNumber);
        addNumber.setPrefWidth(100); // Set preferred width
        addNumber.setPrefHeight(50); // Set preferred height

        //for the number property, because it is a dynamic element, it displayed as a list instead which is why instead of string it is a SimpleListProperty
        numberCol.setCellFactory(column -> new TableCell<Contact, SimpleListProperty<String>>() {
            private final ListView<String> listView = new ListView<>();
            {
                listView.setEditable(true); //allow the list view to be edited
                listView.setCellFactory(TextFieldListCell.forListView());//set the cells in the list to be text fields so that they can be edited
                //setOnEditCommit is important because it only does the follwing after a edit is committed (after the user pressed enter)
                listView.setOnEditCommit(event -> {
                    int index = event.getIndex(); //gets the index of the number that is being edited
                    String newValue = event.getNewValue(); //gets the new string value entered edit
                    Contact contact = getTableView().getItems().get(getIndex()); //gets the contact that is associated with the change
                    contact.updateNumber(index, newValue); //changes the value of the number in the contac itself
                    listView.getItems().set(index, newValue); //sets the value in the listview to the new string value that represents the number
                    try {
                        Contact.updateContacts();//updates the contact and writes the information into the json files
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                deleteNumber.setOnAction(event -> {
                    int index = Integer.parseInt(deleteNumIndex.getText());
                    table.getSelectionModel().getSelectedItem().getNumber().remove(index);
                    deleteNumIndex.clear();
                    listView.getItems().remove(index);
                    try {
                        Contact.updateContacts();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                addNumber.setOnAction(event -> {
                    String newValue = newNumber.getText();
                    table.getSelectionModel().getSelectedItem().getNumber().add(newValue);
                    newNumber.clear();
                    listView.getItems().add(newValue);
                    System.out.println("done?");
                    try {
                        Contact.updateContacts();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            //updates the inherited updateItem value that helps with setting up the list view in the table
            @Override
            protected void updateItem(SimpleListProperty<String> numbers, boolean empty) {
                super.updateItem(numbers, empty);
                if (empty || numbers == null || numbers.isEmpty()) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //sets the size and graphic and what should be in the list view which is numbers
                    listView.setItems(numbers);
                    listView.setMaxHeight(60);
                    listView.setPrefHeight(60);

                    setGraphic(listView);
                }
            }
        });

        //same as the other contact properties
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

        //same as the other contact properties
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

        //sets the size of each colum
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

        //add all the colum to the table
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, numberCol, postalCodeCol, companyCol);

        vbox2.getChildren().addAll(add,delete);
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox2.setAlignment(Pos.CENTER);

        //creates a hbox which will contain the contact vbox, the vbox with the add and delete buttons ,and the gridpane with the textfields
        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox,vbox2, grid);

        //prograom reads all of the contacts from the json file
        Contact.readContacts();
        //reads the contactlist from the contact class and add all the contacts into the data observable data
        for(Contact e:Contact.contactList){
            data.add(e);
            table.setItems(data);
        }

        //sets up the scene and the stage and show on the screen
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