package com.ExamenClient.Cajero.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccesoCuentaResponse {

    private Integer idCuenta; 
    private String mensaje;

    public AccesoCuentaResponse() {
    }

    public AccesoCuentaResponse(Integer idCuenta, String mensaje) {
        this.idCuenta = idCuenta;
        this.mensaje = mensaje;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
