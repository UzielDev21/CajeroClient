package com.ExamenClient.Cajero.ML;

public class Denominacion {

    private int idDenominacion;
    private int valor;
    private String tipo;

    public Denominacion() {
    }

    public Denominacion(int idDenominacion, int valor, String tipo) {
        this.idDenominacion = idDenominacion;
        this.valor = valor;
        this.tipo = tipo;
    }

    public int getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(int idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
