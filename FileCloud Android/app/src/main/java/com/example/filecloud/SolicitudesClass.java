package com.example.filecloud;

public class SolicitudesClass {

    private String usuarioRequisito, usuarioReceptor, Documento, Fecha;
    private int estado;

    public SolicitudesClass () {

    }

    public SolicitudesClass(String usuarioRequisito, String usuarioReceptor, String documento, String Fecha, int estado) {
        this.usuarioRequisito = usuarioRequisito;
        this.estado = estado;
        this.usuarioReceptor = usuarioReceptor;
        Documento = documento;
        this.Fecha = Fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public String getUsuarioReceptor() {
        return usuarioReceptor;
    }

    public void setUsuarioReceptor(String usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }
}
