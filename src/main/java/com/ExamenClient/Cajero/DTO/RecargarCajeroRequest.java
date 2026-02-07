package com.ExamenClient.Cajero.DTO;

public class RecargarCajeroRequest {

    private Integer idUsuario;
    private Integer idCajero;

    public RecargarCajeroRequest() {

    }

    public RecargarCajeroRequest(Integer idUsuario, Integer idCajero) {
        this.idUsuario = idUsuario;
        this.idCajero = idCajero;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(Integer idCajero) {
        this.idCajero = idCajero;
    }

}
