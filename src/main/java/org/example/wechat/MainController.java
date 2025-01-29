package org.example.wechat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MainController {

    @FXML
    public TextField usuario;
    @FXML
    public TextField contraseña;
    public Button logIn;


    @FXML
    protected void onContactosButtonClick() {
        Conexion.connect();
        Usuario placeholder = new Usuario();
        Boolean flag = false;
        for (Usuario u : Conexion.ListaUsuarios) {
            flag = usuario.getText().equals(u.getNombre()) && contraseña.getText().equals(u.getContraseña());
            if (flag) {
                placeholder = u;
                break;
            }
        }
        if (flag){
            Conectar(placeholder);
        }else {
            Error();
        }
    }


    private void Conectar(Usuario usuario) {
        Parent parent = null;
        try {
            ContactosController.usuario = usuario;
            parent = FXMLLoader.load(getClass().getResource("contactos.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Welcome");
            stage.setScene(scene);
            stage.show();
            cerrar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void Error() {
        Stage stage = new Stage();
        Label error = new Label("Usuario o contraseña incorrecto");
        StackPane pane = new StackPane();
        pane.getChildren().add(error);
        Scene xd = new Scene(pane);
        stage.setScene(xd);
        stage.show();
    }

    private void cerrar() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();
    }
}

