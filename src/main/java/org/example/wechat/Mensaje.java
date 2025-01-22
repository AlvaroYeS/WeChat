package org.example.wechat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensaje {
    private String id_chat;
    private String id_emisor;
    private String id_receptor;
    private String mensaje;
    private String fecha_envio;
    private LocalDateTime fecha_envioDate;
    private Estado estado;

    public Mensaje(String id_chat, String id_emisor, String mensaje, String fecha_envio, Estado estado) {
        this.id_chat = id_chat;
        this.id_emisor = id_emisor;
        this.mensaje = mensaje;
        this.fecha_envio = fecha_envio;
        this.estado = estado;
    }

    public Mensaje() {}

    public void initialize() {
        convertDate();
        getReceptor();
    }

    public void getReceptor() {
        StringBuilder builder = new StringBuilder(id_chat);
        id_receptor = String.valueOf(builder.delete(0,10));
    }

    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }

    public String getId_emisor() {
        return id_emisor;
    }

    public void setId_emisor(String id_emisor) {
        this.id_emisor = id_emisor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha_envioDate() {
        return fecha_envioDate;
    }

    public void setFecha_envioDate(LocalDateTime fecha_envioDate) {
        this.fecha_envioDate = fecha_envioDate;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }


    public String getFecha_envio() {
        return fecha_envioDate.format(DateTimeFormatter.ofPattern("DD/MM/YYYY - hh:mm:ss"));
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id_chat='" + id_chat + '\'' +
                ", id_emisor='" + id_emisor + '\'' +
                ", id_receptor='" + id_receptor + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", estado=" + estado +
                ", fecha_envio='" + getFecha_envio() + '\'' +
                '}';
    }

    public void convertDate() {
        StringBuilder builder = new StringBuilder(fecha_envio);
        builder.deleteCharAt(19);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.fecha_envioDate = LocalDateTime.parse(builder, formatter);
    }
}
