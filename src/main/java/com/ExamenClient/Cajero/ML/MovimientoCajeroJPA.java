package com.ExamenClient.Cajero.ML;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovimientoCajeroJPA {

    private int idMovimiento;
    
    @JsonProperty("CajeroJPA")
    public Cajero cajero;

    @JsonProperty("UsuarioJPA")
    public Usuario usuaio;

    private String tipoMovimiento;

    public MovimientoCajeroJPA() {
    }

    public MovimientoCajeroJPA(int idMovimiento, Cajero cajero, Usuario usuario, String tipoMovimiento) {
        this.idMovimiento = idMovimiento;
        this.cajero = cajero;
        this.usuaio = usuario;
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Usuario getUsuaio() {
        return usuaio;
    }

    public void setUsuaio(Usuario usuaio) {
        this.usuaio = usuaio;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

}
