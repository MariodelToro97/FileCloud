package com.example.filecloud;

public class Documentos {

    private String Nombre, Fecha;

    public Documentos(){

    }

    public Documentos(String nombre, String fecha) {
        Nombre = nombre;
        Fecha = fecha;
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
