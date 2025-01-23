package org.example.wechat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private GridPane chat;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        chat.setHgap(10);
        chat.setVgap(10);
        chat.setPadding(new Insets(10, 10, 10, 10));
        for (int i = 0; i < Conexion.ListaMensajes.size(); i++) {
            String estado = "";

            if (Conexion.ListaMensajes.get(i).getEstado().equals(Estado.no_entregado)) {
                estado = "✓";
            } else if (Conexion.ListaMensajes.get(i).getEstado().equals(Estado.entregado)) {
                estado = "✓✓";
            } else {
                estado = "✅✅";
            }

            Bubble bubble = new Bubble(Conexion.ListaMensajes.get(i).getMensaje(), (Conexion.ListaMensajes.get(i).getFecha_envioDate().format(DateTimeFormatter.ofPattern("hh:mm")) + " " + estado));

            if (Objects.equals(Conexion.ListaMensajes.get(i).getId_emisor(), "123456789")) {
                GridPane.setHalignment(bubble, HPos.RIGHT);
            } else {
                bubble = new Bubble(Conexion.ListaMensajes.get(i).getMensaje(),Conexion.ListaMensajes.get(i).getFecha_envioDate().format(DateTimeFormatter.ofPattern("hh:mm")));
                GridPane.setHalignment(bubble, HPos.LEFT);
            }

            chat.addRow(i, bubble);
        }
    }
}
