package org.example.wechat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onChatButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        Conexion.connect();
        Conexion.ordenarMensajes();
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("chat.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Welcome");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onContactosButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        Conexion.connect();
        //Conexion.ordenarMensajes();
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("contactos.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Welcome");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}