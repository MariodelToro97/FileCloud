package com.example.filecloud;

public class Documentos {

    private String Nombre, Fecha, Usuario, ARCHIVO;

    public Documentos(){

    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public Documentos(String nombre, String fecha, String usuario) {
        Nombre = nombre;
        Fecha = fecha;
        Usuario = usuario;
    }

    public String getARCHIVO() {
        return ARCHIVO;
    }

    public void setARCHIVO(String ARCHIVO) {
        this.ARCHIVO = ARCHIVO;
    }

    public Documentos (String ARCHIVO) {
        this.ARCHIVO = ARCHIVO;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
