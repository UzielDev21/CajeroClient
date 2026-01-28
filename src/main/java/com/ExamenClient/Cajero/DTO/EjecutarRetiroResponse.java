package com.ExamenClient.Cajero.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EjecutarRetiroResponse {

    @JsonIgnore
    private int codigo;

    private String mensaje;

    public EjecutarRetiroResponse() {

    }

    public EjecutarRetiroResponse(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
