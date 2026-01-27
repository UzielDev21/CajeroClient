package com.ExamenClient.Cajero.ML;

public class Cajero {

    private int idCajero;
    private String ubicacion;
    private int estado;

    public Cajero() {
    }

    public Cajero(int idCajero, String ubicacion, int estado) {
        this.idCajero = idCajero;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
