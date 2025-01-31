package org.example.wechat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChatController implements Initializable {


    public TextField mensaje;
    public ScrollPane scrollPane;
    public Text nombre;
    @FXML
    private GridPane chat;

    public static String id;
    public static String id2;
    public static String idUser;
    public static String nombreUser;
    public ArrayList<Mensaje> listaMensajes;
    private boolean id_1_2 = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nombre.setText(nombreUser);

        chat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        chat.setHgap(10);
        chat.setVgap(10);
        chat.setPadding(new Insets(10, 10, 10, 10));

        Conexion.ordenarMensajes();
        listaMensajes = (ArrayList<Mensaje>) Conexion.ListaMensajes.stream().filter(mensaje -> mensaje.getId_chat().contains(id) || mensaje.getId_chat().contains(id2)).collect(Collectors.toList());
        System.out.println(listaMensajes);

        for (int i = 0; i < listaMensajes.size(); i++) {

            if (!id.equals(listaMensajes.get(i).getId_chat())){
                if (!id2.equals(listaMensajes.get(i).getId_chat())) {
                    id_1_2 = false;
                    break;
                }
            }
            String estado;

            if (listaMensajes.get(i).getEstado().equals(Estado.no_entregado)) {
                estado = "✓";
            } else if (listaMensajes.get(i).getEstado().equals(Estado.entregado)) {
                estado = "✓✓";
            } else {
                estado = "✅✅";
            }

            Bubble bubble = new Bubble(listaMensajes.get(i).getMensaje(), (listaMensajes.get(i).getFecha_envioDate().format(DateTimeFormatter.ofPattern("hh:mm")) + " " + estado));

            if (Objects.equals(listaMensajes.get(i).getId_emisor(), idUser)) {
                GridPane.setHalignment(bubble, HPos.RIGHT);
            } else {
                bubble = new Bubble(listaMensajes.get(i).getMensaje(),listaMensajes.get(i).getFecha_envioDate().format(DateTimeFormatter.ofPattern("hh:mm")));
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
        Mensaje m;
        StringBuilder builder;
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
            listaMensajes.add(m);
        }
    }

    @FXML
    private void volver() {
        Parent parent;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("contactos.fxml")));
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
        Stage stage = (Stage) chat.getScene().getWindow();
        stage.close();
    }
}
