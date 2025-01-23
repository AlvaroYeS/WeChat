package org.example.wechat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContactosController implements Initializable {

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
                contactos.add(boton, 0, i);
            }
        }
    }
}
