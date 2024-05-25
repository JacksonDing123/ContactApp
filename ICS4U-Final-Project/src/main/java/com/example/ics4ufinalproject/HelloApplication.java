package com.example.ics4ufinalproject;

import javafx.animation.PauseTransition;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;

public class HelloApplication extends Application {

    //creat table view and observable list to show the data
    private TableView<Contact> table = new TableView<Contact>();
    //make the observable list to be displayed on the table
    private final ObservableList<Contact> data = FXCollections.observableArrayList();

    /**
     * Start method where the main JavaFX Program runs
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        //creates the warning for when the input is wrong, and set it to invisible initially
        Label warning = new Label("invalid input");
        warning.setVisible(false);

        //creates the vbox for the add,delete, addNumber, deleteNumber buttons with proper spacing settings
        final VBox vbox2 = new VBox();

        //title for the contact app
        final Label label = new Label("Contacts");
        label.setFont(new Font("Arial", 20));

        //creates a Vbox for the title f the table and the table
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
        grid.add(warning,0,8);

        // creates textfields for all the informations of a contact
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

        //button that adds the contact
        Button add = new Button("Add");
        add.setPrefWidth(100); // Set preferred width
        add.setPrefHeight(50); // Set preferred height

        //below is what the program execute when add is pressed
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(firstNameField.getText().equals("")&&lastNameField.getText().equals("")&&emailField.getText().equals("")&&numberField.getText().equals("")&&postalCodeField.getText().equals("")&&companyField.getText().equals("")){
                    //if all of the fields are clear then do nothing
                }else
                    //if the number field is something other than numbers or comas, or the email section doesn't contain the @ sign then the program doesn't add the contact because input is invalid
                    if((!numberField.getText().matches("[0-9,]*")&&!numberField.getText().equals(""))||(!emailField.getText().contains("@")&&!emailField.getText().equals(""))){
                        //show the warning label for 3 seconds
                        warning.setVisible(true);
                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(event -> warning.setVisible(false));
                        pause.play();
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

        //add the above two buttons to the vbox2 and set the style for the vbox
        vbox2.getChildren().addAll(add,delete);
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox2.setAlignment(Pos.CENTER);

        //creates a hbox which will contain the contact vbox, the vbox with the add and delete buttons ,and the gridpane with the textfields
        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(vbox,vbox2, grid);

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

        //the email colum is special due to input validation and custom cell renderings
        TableColumn<Contact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellFactory(column -> new TextFieldTableCell<>(new DefaultStringConverter()) {

            // Generate a random light color once for the entire column
            double minBrightness = 0.7; // minimum brightness for light colors
            double red = minBrightness + (Math.random() * (1.0 - minBrightness));
            double green = minBrightness + (Math.random() * (1.0 - minBrightness));
            double blue = minBrightness + (Math.random() * (1.0 - minBrightness));
            Color randomColor = Color.color(red, green, blue);
            String colorString = toRgbString(randomColor);

            /**
             * Method to convert Color to RGB string
             */
            private String toRgbString(Color color) {
                return String.format("rgb(%d, %d, %d)",
                        (int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255),
                        (int) (color.getBlue() * 255));
            }

            /**
             * method used to update each cell of the email colum everything it is changed
             * @param item
             * @param empty
             */
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    //sets the text and style when the data in the cell doesn't exist
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    //other wise sets the item and a random colour for the custom cell rendering
                    setText(item);
                    setStyle("-fx-background-color: " + colorString + "; -fx-border-color: lightgrey; -fx-border-width: 0 0 1 1;");
                }
            }
        });

        //when the email commits an edit
        emailCol.setOnEditCommit(event -> {
            //gets the position and row of the email
            TablePosition<Contact, String> pos = event.getTablePosition();
            int row = pos.getRow();
            //if the new edited value has @ then it changes the value and refreshes the table
            if(event.getNewValue().contains("@")){
                event.getRowValue().setEmail(event.getNewValue());
                table.refresh();
            }else{
                //if the new value doesn't satisfy the input specification, then warning comes out for 3 seconds and refreshes the table
                warning.setVisible(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(thisevent -> warning.setVisible(false));
                pause.play();
                table.refresh();
            }
            try {
                //updates the contact in case anything has changed
                Contact.updateContacts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //setting up colum for numbers
        TableColumn<Contact, SimpleListProperty<String>> numberCol = new TableColumn<>("Numbers");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        //setting up button to delete a number
        Button deleteNumber = new Button("Delete Number");
        vbox2.getChildren().add(deleteNumber);
        deleteNumber.setPrefWidth(100); // Set preferred width
        deleteNumber.setPrefHeight(50); // Set preferred height

        //setting up button to add a button to a contact
        Button addNumber = new Button("Add Number");
        vbox2.getChildren().add(addNumber);
        addNumber.setPrefWidth(100); // Set preferred width
        addNumber.setPrefHeight(50); // Set preferred height

        //for the number property, because it is a dynamic element, it displayed as a list instead which is why instead of string it is a SimpleListProperty
        numberCol.setCellFactory(column -> new TableCell<Contact, SimpleListProperty<String>>() {
            private final ListView<String> listView = new ListView<>();//creates a listView for the numbers
            {
                listView.setEditable(true); //allow the list view to be edited
                listView.setCellFactory(TextFieldListCell.forListView());//set the cells in the list to be text fields so that they can be edited
                //setOnEditCommit is important because it only does the follwing after a edit is committed (after the user pressed enter)
                listView.setOnEditCommit(event -> {
                    //input validation has to include numbers
                    if(event.getNewValue().matches("[0-9]*")){
                        int index = event.getIndex(); //gets the index of the number that is being edited
                        String newValue = event.getNewValue(); //gets the new string value entered edit
                        Contact contact = getTableView().getItems().get(getIndex()); //gets the contact that is associated with the change
                        contact.updateNumber(index, newValue); //changes the value of the number in the contac itself
                        listView.getItems().set(index, newValue); //sets the value in the listview to the new string value that represents the number
                    }else{
                        //sets a warning if the input doesn't fit the input specifications
                        warning.setVisible(true);
                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(thisevent -> warning.setVisible(false));
                        pause.play();
                    }
                    try {
                        Contact.updateContacts();//updates the contact and writes the information into the json files
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                //deletes the number based on the order it is in the list of numbers
                deleteNumber.setOnAction(event -> {
                    int index = Integer.parseInt(deleteNumIndex.getText()); //gets the index input from the textfield
                    table.getSelectionModel().getSelectedItem().getNumber().remove(index-1); //deletes that corresponding index but minus one because everything starts at 0
                    deleteNumIndex.clear(); //clears the text field
                    listView.getItems().remove(index-1); //updates it in the list view
                    try {
                        Contact.updateContacts(); //updates the contact in case when there are changes
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                //adds a number into a contact
                addNumber.setOnAction(event -> {
                    String newValue = newNumber.getText();//gets the number from the textfield

                    //if input matches the input specification and it is not blank adds it into the contactlist and into the listView
                    if(newValue.matches("[0-9]*")&&!newValue.equals("")){
                        table.getSelectionModel().getSelectedItem().getNumber().add(newValue);
                        newNumber.clear(); //clears the text field for later
                        listView.getItems().add(newValue);
                    }else{
                        //otherwise, warn the user and clear the texfield
                        warning.setVisible(true);

                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(thisevent -> warning.setVisible(false));
                        pause.play();
                        newNumber.clear();
                    }
                    try {
                        Contact.updateContacts(); //updates in case of any change
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