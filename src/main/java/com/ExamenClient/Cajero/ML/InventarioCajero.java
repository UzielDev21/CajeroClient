package com.ExamenClient.Cajero.ML;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InventarioCajero {

    private int idInventario;

    @JsonProperty("CajeroJPA")
    public Cajero cajero;

    @JsonProperty("DenominacionJPA")
    public Denominacion denominacion;

    private float cantidad;

    public InventarioCajero() {
    }

    public InventarioCajero(int idInventario, Cajero cajero, Denominacion denominacion, float cantidad) {
        this.idInventario = idInventario;
        this.cajero = cajero;
        this.denominacion = denominacion;
        this.cantidad = cantidad;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Denominacion getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

}
