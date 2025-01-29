package org.example.wechat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.robot.Robot;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatController implements Initializable {


    public TextField mensaje;
    public ScrollPane scrollPane;
    @FXML
    private GridPane chat;

    public static String id;
    public static String id2;
    public static String idUser;
    public ArrayList<Mensaje> listaMensajes;
    private boolean id_1_2 = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        chat.setHgap(10);
        chat.setVgap(10);
        chat.setPadding(new Insets(10, 10, 10, 10));

        Conexion.ordenarMensajes();

        for (int i = 0; i < Conexion.ListaMensajes.size(); i++) {

            if (!id.equals(Conexion.ListaMensajes.get(i).getId_chat())){
                if (!id2.equals(Conexion.ListaMensajes.get(i).getId_chat())) {
                    id_1_2 = false;
                    break;
                }
            }
            String estado = "";

            if (Conexion.ListaMensajes.get(i).getEstado().equals(Estado.no_entregado)) {
                estado = "✓";
            } else if (Conexion.ListaMensajes.get(i).getEstado().equals(Estado.entregado)) {
                estado = "✓✓";
            } else {
                estado = "✅✅";
            }

            Bubble bubble = new Bubble(Conexion.ListaMensajes.get(i).getMensaje(), (Conexion.ListaMensajes.get(i).getFecha_envioDate().format(DateTimeFormatter.ofPattern("hh:mm")) + " " + estado));

            if (Objects.equals(Conexion.ListaMensajes.get(i).getId_emisor(), idUser)) {
                GridPane.setHalignment(bubble, HPos.RIGHT);
            } else {
                bubble = new Bubble(Conexion.ListaMensajes.get(i).getMensaje(),Conexion.ListaMensajes.get(i).getFecha_envioDate().format(DateTimeFormatter.ofPattern("hh:mm")));
                GridPane.setHalignment(bubble, HPos.LEFT);
            }

            chat.addRow(i, bubble);
        }

        scrollPane.setHvalue(chat.getRowCount());
    }

    @FXML
    private void openEmojiKeyboard() {
        try {
            Robot robot = new Robot();
            // Simula pulsar la tecla Windows
            robot.keyPress(KeyCode.WINDOWS);
            // Simula pulsar la tecla "."
            robot.keyPress(KeyCode.PERIOD);

            // Suelta las teclas
            robot.keyRelease(KeyCode.WINDOWS);
            robot.keyRelease(KeyCode.PERIOD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void onSendClick() {
        Mensaje m = null;
        StringBuilder builder = null;
        if (id_1_2) {
            m = new Mensaje(id2, idUser, mensaje.getText(), LocalDateTime.now().toString(), Estado.no_entregado);
            builder = new StringBuilder(id2);
        } else {
            m = new Mensaje(id, idUser, mensaje.getText(), LocalDateTime.now().toString(), Estado.no_entregado);
            builder = new StringBuilder(id);
        }
        m.setId_receptor(String.valueOf(builder.delete(0,10)));
        m.convertDate();
        Bubble bubble = new Bubble(m.getMensaje(), (m.getFecha_envioDate().format(DateTimeFormatter.ofPattern("hh:mm")) + " ✓"));
        GridPane.setHalignment(bubble, HPos.RIGHT);
        chat.addRow(chat.getRowCount(), bubble);

        if (m!=null) {
            Conexion.insertMensaje(m);
        }

    }
}
