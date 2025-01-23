package org.example.wechat;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Usuario {

    @BsonProperty("_id")
    private String id; // MongoDB genera automáticamente un ObjectId si no se asigna uno.

    private String nombre;

    private String email;

    private String estado;

    @BsonProperty("ultimo_acceso")
    private String ultimoAcceso;// Para almacenar la fecha y hora como un tipo de datos Instant (equivalente a Date en MongoDB)

    private LocalDateTime ultimoAccesoDate;
    private String contraseña;

    // Constructor
    public Usuario() {}

    public Usuario(String nombre, String email, String estado, String ultimoAcceso, String contraseña) {
        this.nombre = nombre;
        this.email = email;
        this.estado = estado;
        this.ultimoAcceso = ultimoAcceso;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUltimoAcceso() {
        return ultimoAccesoDate.format(DateTimeFormatter.ofPattern("DD/MM/YYYY - hh:mm:ss"));
    }

    public LocalDateTime getUltimoAccesoDate() {
        return ultimoAccesoDate;
    }

    public void setUltimoAccesoDate(LocalDateTime ultimoAccesoDate) {
        this.ultimoAccesoDate = ultimoAccesoDate;
    }

    public void setUltimoAcceso(String ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", estado='" + estado + '\'' +
                ", ultimo_Acceso=" + getUltimoAcceso() +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }

    public void convertDate() {
        StringBuilder builder = new StringBuilder(ultimoAcceso);
        builder.deleteCharAt(19);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.ultimoAccesoDate = LocalDateTime.parse(builder, formatter);
    }
}
