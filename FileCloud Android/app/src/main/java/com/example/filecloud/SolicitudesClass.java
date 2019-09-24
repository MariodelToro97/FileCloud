package com.example.filecloud;

public class SolicitudesClass {

    private String usuarioRequisito, Documento, Fecha, mensaje, user;

    SolicitudesClass(String usuarioRequisito, String documento, String Fecha, String mensaje, String user) {
        this.usuarioRequisito = usuarioRequisito;
        Documento = documento;
        this.Fecha = Fecha;
        this.mensaje = mensaje;
        this.user = user;
    }

    String getMensaje() {
        return mensaje;
    }

    String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    String getUsuarioRequisito() {
        return usuarioRequisito;
    }

    public void setUsuarioRequisito(String usuarioRequisito) {
        this.usuarioRequisito = usuarioRequisito;
    }

    String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }
}
