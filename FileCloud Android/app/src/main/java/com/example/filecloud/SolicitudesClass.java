package com.example.filecloud;

import android.util.Log;

public class SolicitudesClass {

    private String usuarioRequisito, Documento, Fecha, mensaje, user;

    public SolicitudesClass(String usuarioRequisito, String documento, String Fecha, String mensaje, String user) {
        this.usuarioRequisito = usuarioRequisito;
        Documento = documento;
        this.Fecha = Fecha;
        this.mensaje = mensaje;
        this.user = user;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getUsuarioRequisito() {
        return usuarioRequisito;
    }

    public void setUsuarioRequisito(String usuarioRequisito) {
        this.usuarioRequisito = usuarioRequisito;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }
}
