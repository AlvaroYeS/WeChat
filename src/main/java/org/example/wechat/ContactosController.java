package org.example.wechat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactosController implements Initializable {

    public Button volver;
    @FXML
    private GridPane contactos;
    public static Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactos.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        contactos.setHgap(10);
        contactos.setVgap(10);
        contactos.setPadding(new Insets(10, 10, 10, 10));
        for (int i = 0; i < Conexion.ListaUsuarios.size(); i++) {
            if(!Conexion.ListaUsuarios.get(i).equals(usuario)) {
                Button boton = new Button(Conexion.ListaUsuarios.get(i).getNombre());
                int finalI = i;
                boton.setOnAction(e -> abrirChat(Conexion.ListaUsuarios.get(finalI).getId(), Conexion.ListaUsuarios.get(finalI).getNombre()));
                contactos.add(boton, 0, i);
            }
        }
    }

     public void abrirChat(String id, String nombre){
            Conexion.getMensajes();
            Parent parent = null;
             try {
                 ChatController.id = usuario.getId() + "-" + id;
                 ChatController.idUser = usuario.getId();
                 ChatController.id2 = id + "-" + usuario.getId();
                 ChatController.nombreUser = nombre;
                 parent = FXMLLoader.load(getClass().getResource("chat.fxml"));
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

    public void volver() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
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

    private void cerrar() {
        Stage stage = (Stage) volver.getScene().getWindow();
        stage.close();
    }
}
