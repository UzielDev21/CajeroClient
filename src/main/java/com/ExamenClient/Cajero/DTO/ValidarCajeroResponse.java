package com.ExamenClient.Cajero.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ValidarCajeroResponse {

    @JsonIgnore
    private Integer codigo;

    private String mensaje;

    public ValidarCajeroResponse() {
    }

    public ValidarCajeroResponse(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
