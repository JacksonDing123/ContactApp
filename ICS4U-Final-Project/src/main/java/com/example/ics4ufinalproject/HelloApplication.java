package com.example.ics4ufinalproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;

public class HelloApplication extends Application {

    private TableView table = new TableView();

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(300);
        stage.setHeight(500);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        Button add = new Button("Add");
        Button delete = new Button("Delete");

        final VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(add,delete);
        vbox2.setAlignment(Pos.CENTER);

        final HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox,vbox2);

        ((Group) scene.getRoot()).getChildren().addAll(hbox);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}