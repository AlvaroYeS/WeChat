package org.example.wechat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MainController {


    public TextField usuario;
    public TextField contraseña;
    public Button logIn;

    @FXML
    protected void onContactosButtonClick() {

        Conexion.connect();
        for (Usuario u:  Conexion.ListaUsuarios) {
            if (usuario.getText().equals(u.getNombre())){
                if (contraseña.getText().equals(u.getContraseña()))
            }
        }       Conexion.ListaMensajes;
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